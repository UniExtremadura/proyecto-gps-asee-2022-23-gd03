package es.unex.parsiapp.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.unex.parsiapp.AppExecutors;
import es.unex.parsiapp.ListAdapterFolder;
import es.unex.parsiapp.R;
import es.unex.parsiapp.databinding.FragmentFolderBinding;
import es.unex.parsiapp.folderContentActivity;
import es.unex.parsiapp.model.Carpeta;
import es.unex.parsiapp.roomdb.ParsiDatabase;

public class FolderFragment extends Fragment {

    private FolderViewModel mViewModel;
    private FragmentFolderBinding binding; // Binding
    private List<Carpeta> listCarpeta;

    public static FolderFragment newInstance() {
        return new FolderFragment();
    }

    private View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFolderBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        showFolders(root);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        showFolders(root);
    }

    public void showFolders(View root){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // Declaracion de la instancia de la BD
                ParsiDatabase database = ParsiDatabase.getInstance(root.getContext());

                listCarpeta = database.getCarpetaDao().getAll();

                if(listCarpeta != null) {
                    ListAdapterFolder listAdapter = new ListAdapterFolder(listCarpeta,root.getContext() , new ListAdapterFolder.OnItemClickListener() {
                        @Override
                        public void onItemClick(Carpeta item) {
                            moveToFolderContent(item, root);
                        }
                    });
                    // La UI debe de ejecutarse en un mainThread (si no, peta)
                    AppExecutors.getInstance().mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            RecyclerView recyclerView = root.findViewById(R.id.listRecyclerView);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
                            recyclerView.setAdapter(listAdapter);
                        }
                    });
                }
            }
        });
    }

    public void moveToFolderContent(Carpeta item, View root){
        Intent intent = new Intent(root.getContext(), folderContentActivity.class);
        intent.putExtra("folderContent", item);
        startActivity(intent);
    }
}