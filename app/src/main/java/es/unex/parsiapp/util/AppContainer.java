package es.unex.parsiapp.util;

import android.content.Context;

import es.unex.parsiapp.repository.PostRepository;
import es.unex.parsiapp.roomdb.ParsiDatabase;
import es.unex.parsiapp.twitterapi.PostNetworkDataSource;
import es.unex.parsiapp.ui.CreateColumnViewModelFactory;
import es.unex.parsiapp.ui.CreateFolderViewModelFactory;
import es.unex.parsiapp.ui.DeleteViewModelFactory;
import es.unex.parsiapp.ui.FolderContentViewModelFactory;
import es.unex.parsiapp.ui.LoginViewModelFactory;
import es.unex.parsiapp.ui.MenuLateralViewModelFactory;
import es.unex.parsiapp.ui.RegisterViewModelFactory;
import es.unex.parsiapp.ui.TweetDetailsViewModelFactory;
import es.unex.parsiapp.ui.columns.ColumnasViewModelFactory;
import es.unex.parsiapp.ui.gallery.FolderViewModelFactory;
import es.unex.parsiapp.ui.home.HomeViewModelFactory;

public class AppContainer {

    private ParsiDatabase database;
    private PostNetworkDataSource networkDataSource;
    public PostRepository repository;

    // Factories
    public HomeViewModelFactory Hfactory;
    public FolderViewModelFactory Ffactory;
    public ColumnasViewModelFactory Cfactory;
    public FolderContentViewModelFactory FCfactory;
    public CreateColumnViewModelFactory CCfactory;
    public CreateFolderViewModelFactory CFfactory;
    public DeleteViewModelFactory Dfactory;
    public LoginViewModelFactory Lfactory;
    public MenuLateralViewModelFactory MLfactory;
    public RegisterViewModelFactory Rfactory;
    public TweetDetailsViewModelFactory TDfactory;


    public AppContainer(Context context){
        database = ParsiDatabase.getInstance(context);
        networkDataSource = PostNetworkDataSource.getInstance();
        repository = PostRepository.getInstance(database.getPostDao(), database.getCarpetaDao(), database.getColumnaDao(), database.getUsuarioDao(), networkDataSource);
        Hfactory = new HomeViewModelFactory(repository);
        Ffactory = new FolderViewModelFactory(repository);
        Cfactory = new ColumnasViewModelFactory(repository);
        FCfactory = new FolderContentViewModelFactory(repository);
        CCfactory = new CreateColumnViewModelFactory(repository);
        CFfactory = new CreateFolderViewModelFactory(repository);
        Dfactory = new DeleteViewModelFactory(repository);
        Lfactory = new LoginViewModelFactory(repository);
        MLfactory = new MenuLateralViewModelFactory(repository);
        Rfactory = new RegisterViewModelFactory(repository);
        TDfactory = new TweetDetailsViewModelFactory(repository);
    }

}
