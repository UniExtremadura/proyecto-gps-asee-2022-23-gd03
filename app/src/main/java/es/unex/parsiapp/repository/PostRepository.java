package es.unex.parsiapp.repository;

import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.unex.parsiapp.model.Carpeta;
import es.unex.parsiapp.model.Columna;
import es.unex.parsiapp.model.Post;
import es.unex.parsiapp.model.Usuario;
import es.unex.parsiapp.roomdb.CarpetaDao;
import es.unex.parsiapp.roomdb.ColumnaDao;
import es.unex.parsiapp.roomdb.PostDao;
import es.unex.parsiapp.roomdb.UsuarioDao;
import es.unex.parsiapp.twitterapi.PostNetworkDataSource;
import es.unex.parsiapp.util.AppExecutors;

public class PostRepository {
    private static final String LOG_TAG = PostRepository.class.getSimpleName();

    // For Singleton instantiation
    private static PostRepository sInstance;

    // DAOs
    private final PostDao mPostDao;
    private final CarpetaDao mCarpetaDao;
    private final ColumnaDao mColumnaDao;
    private final UsuarioDao mUsuarioDao;

    private final LiveData<List<Carpeta>> carpetaList;
    private final LiveData<List<Columna>> columnaList;


    private final PostNetworkDataSource mPostNetworkDataSource;
    private final AppExecutors mExecutors = AppExecutors.getInstance();

    // MutableLiveData
    private final MutableLiveData<Columna> columnaFilterLiveData = new MutableLiveData<>();
    private final MutableLiveData<Long> carpetaFilterLiveData = new MutableLiveData<>();
    private final MutableLiveData<Long> columnaBeingEditedFilterLiveData = new MutableLiveData<>();
    private final MutableLiveData<Long> carpetaBeingEditedFilterLiveData = new MutableLiveData<>();
    private final MutableLiveData<Long> columnaToDeleteFilterLiveData = new MutableLiveData<>();
    private final MutableLiveData<Long> carpetaToDeleteFilterLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> userFilterLiveData = new MutableLiveData<>();

    // Refresh
    private final Map<String, Long> lastUpdateTimeMillisMap = new HashMap<>();
    private static final long MIN_TIME_FROM_LAST_FETCH_MILLIS = 5000;

    private PostRepository(PostDao postDao, CarpetaDao folderDao, ColumnaDao colDao, UsuarioDao userDao, PostNetworkDataSource postNetworkDataSource){
        mPostDao = postDao;
        mCarpetaDao = folderDao;
        mColumnaDao = colDao;
        mUsuarioDao = userDao;

        carpetaList = mCarpetaDao.getAllLiveData();
        columnaList = mColumnaDao.getAllFromLiveData();

        mPostNetworkDataSource = postNetworkDataSource;

        // Live Data que obtiene los Posts de la red:
        LiveData<Post[]> networkData = mPostNetworkDataSource.getCurrentPosts();
        networkData.observeForever(newPostsFromNetwork -> {
            System.out.println("Detectados nuevos posts a través del Observer");
            mExecutors.diskIO().execute(() -> {
                if(newPostsFromNetwork.length > 0){
                    System.out.println("BORRANDO ANTIGUOS POSTS DE ROOM");
                    mPostDao.deleteAllPostsWithoutCarpeta();
                }
                List<Post> postList = Arrays.asList(newPostsFromNetwork);
                for(Post p: postList){
                    p.setIdCarpeta(-1);
                }
                postDao.bulkInsert(postList);
                Log.d(LOG_TAG, "New values inserted in Room");
            });
        });
    }

    public synchronized static PostRepository getInstance(PostDao pdao, CarpetaDao fdao, ColumnaDao cdao, UsuarioDao udao, PostNetworkDataSource nds){
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            sInstance = new PostRepository(pdao, fdao, cdao, udao, nds);
            Log.d(LOG_TAG, "Made new repository");
        }
        return sInstance;
    }

    /* MÉTODOS RELACIONADOS CON LA OBTENCIÓN DE POSTS */

    // Obtener Posts cuando se cambia la columna actual
    public void setColumna(String max_posts){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Columna c = mColumnaDao.getColumnaActual();
                if(c != null){
                    AppExecutors.getInstance().mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            columnaFilterLiveData.setValue(c);
                        }
                    });
                    if(isFetchNeeded(c)){
                        doFetchPosts(c, max_posts);
                    }
                }
            }
        });
    }

    // Cambiar la carpeta en la que se está actualmente
    public void setCarpeta(final Carpeta c){
        carpetaFilterLiveData.setValue(c.getIdDb());
    }

    // Cambiar la columna actual
    public void setColumnaActual(Columna c){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // Obtencion columna actual
                Columna oldC = mColumnaDao.getColumnaActual();
                if(oldC != null){
                    oldC.setColumnaActual(false);
                    mColumnaDao.update(oldC);
                }
                mColumnaDao.update(c);
            }
        });
    }

    // Guardar un Post en una carpeta
    public void insertPost(Post pOld, long folder_id){
        Post p = new Post(pOld.getId(), folder_id);
        p.setContenido(pOld.getContenido());
        p.setTimestamp(pOld.getTimestamp());
        p.setAuthorUsername(pOld.getAuthorUsername());
        p.setProfilePicture(pOld.getProfilePicture());
        p.setAuthorId(pOld.getAuthorId());
        mPostDao.insert(p);
    }

    // Buscar nuevos Posts en base a una columna
    public void doFetchPosts(Columna c, String max_posts){
        Log.d(LOG_TAG, "Fetching posts from API");
        System.out.println("Fetching posts from API");
        AppExecutors.getInstance().diskIO().execute(() -> {
            mPostDao.deleteAllPostsWithoutCarpeta();
            mPostNetworkDataSource.fetchPosts(c, max_posts);
            lastUpdateTimeMillisMap.put(c.getApiCall(), System.currentTimeMillis());
        });
    }

    // Obtener Posts de la pantalla de Inicio
    public LiveData<List<Post>> getCurrentPostsHome() {
        System.out.println("OBTENIENDO TODOS LOS POSTS DE HOME (POST REPOSITORY)");
        return Transformations.switchMap(columnaFilterLiveData, new Function<Columna, LiveData<List<Post>>>() {
            @Override
            public LiveData<List<Post>> apply(Columna input) {
                return mPostDao.getAllLiveData();
            }
        });
    }

    // Obtener Posts de una carpeta
    public LiveData<List<Post>> getCurrentPostsFolder() {
        return Transformations.switchMap(carpetaFilterLiveData, new Function<Long, LiveData<List<Post>>>() {
            @Override
            public LiveData<List<Post>> apply(Long input) {
                System.out.println("OBTENIENDO TODOS LOS POSTS DE UNA CARPETA (POST REPOSITORY)");
                return mPostDao.getAllPostsFromCarpetaLiveData(input);
            }
        });
    }

    /* OPERACIONES CON COLUMNAS */

    public void setColumnBeingEdited(long id_columna){
        columnaBeingEditedFilterLiveData.setValue(id_columna);
    }

    public LiveData<Columna> getColumnBeingEdited(){
       return Transformations.switchMap(columnaBeingEditedFilterLiveData, new Function<Long, LiveData<Columna>>() {
           @Override
           public LiveData<Columna> apply(Long input) {
               return mColumnaDao.getColumnaLiveData(input);
           }
       });
    }

    public void setColumnaToDelete(long id_columna){
        columnaToDeleteFilterLiveData.setValue(id_columna);
    }

    public LiveData<Columna> getColumnaToDelete(){
        return Transformations.switchMap(columnaToDeleteFilterLiveData, new Function<Long, LiveData<Columna>>() {
            @Override
            public LiveData<Columna> apply(Long input) {
                return mColumnaDao.getColumnaLiveData(input);
            }
        });
    }

    public LiveData<List<Columna>> getAllColumnas(){
        return columnaList;
    }

    public void createColumna(Columna c){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Columna oldC = mColumnaDao.getColumnaActual();
                if(oldC != null){
                    oldC.setColumnaActual(false);
                    mColumnaDao.update(oldC);
                }
                mColumnaDao.insert(c);
            }
        });
    }

    public void editColumna(Columna c){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mColumnaDao.update(c);
            }
        });
    }

    public void deleteColumna(long id_columna){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mColumnaDao.deleteColumnaByID(id_columna);

                // Selecciona (si puede) una nueva columna como actual
                try {
                    Columna newActual = mColumnaDao.getAll().get(0);
                    newActual.setColumnaActual(true);
                    mColumnaDao.update(newActual);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    /* OPERACIONES CON CARPETAS */

    public void setCarpetaBeingEdited(long id_carpeta){
        carpetaBeingEditedFilterLiveData.setValue(id_carpeta);
    }

    public LiveData<Carpeta> getCarpetaBeingEdited(){
        return Transformations.switchMap(carpetaBeingEditedFilterLiveData, new Function<Long, LiveData<Carpeta>>() {
            @Override
            public LiveData<Carpeta> apply(Long input) {
                return mCarpetaDao.getFolderLiveData(input);
            }
        });
    }

    public void setCarpetaToDelete(long id_carpeta){
        carpetaToDeleteFilterLiveData.setValue(id_carpeta);
    }

    public LiveData<Carpeta> getCarpetaToDelete(){
        return Transformations.switchMap(carpetaToDeleteFilterLiveData, new Function<Long, LiveData<Carpeta>>() {
            @Override
            public LiveData<Carpeta> apply(Long input) {
                return mCarpetaDao.getFolderLiveData(input);
            }
        });
    }

    public LiveData<List<Carpeta>> getAllFolders(){
        return carpetaList;
    }

    public void createFolder(Carpeta c){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mCarpetaDao.insert(c);
            }
        });
    }

    public void editFolder(Carpeta c){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mCarpetaDao.update(c);
            }
        });
    }

    public void deleteFolder(long id_carpeta){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mPostDao.deleteAllPostsFromCarpeta(id_carpeta);
                mCarpetaDao.deleteFolderByID(id_carpeta);
            }
        });
    }

    /* OPERACIONES CON USUARIO */

    public void setUser(String username){
        userFilterLiveData.setValue(username);
    }

    public LiveData<Usuario> getUser(){
        return Transformations.switchMap(userFilterLiveData, new Function<String, LiveData<Usuario>>() {
            @Override
            public LiveData<Usuario> apply(String input) {
                return mUsuarioDao.getUsuarioFromUsernameLiveData(input);
            }
        });
    }

    public void createUser(Usuario u){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mUsuarioDao.insert(u);
            }
        });
    }

    /* GUARDADO DE POSTS */

    public void savePost(long post_id, long folder_id){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Post pOld = mPostDao.getPost(post_id);
                insertPost(pOld, folder_id);
            }
        });
    }

    public void savePostDetails(String post_id, long folder_id){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Post pOld = mPostDao.getPostById(post_id);
                insertPost(pOld, folder_id);
            }
        });
    }

    /* COOLDOWN DE REFRESCAR POSTS */

    private boolean isFetchNeeded(Columna c){
        Long lastFetchTimeMillis = lastUpdateTimeMillisMap.get(c.getApiCall());
        lastFetchTimeMillis = lastFetchTimeMillis == null ? 0L : lastFetchTimeMillis;
        long timeFromLastFetch = System.currentTimeMillis() - lastFetchTimeMillis;
        return timeFromLastFetch > MIN_TIME_FROM_LAST_FETCH_MILLIS;
    }
}
