package es.unex.parsiapp.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.unex.parsiapp.MyApplication;
import es.unex.parsiapp.R;
import es.unex.parsiapp.databinding.FragmentFolderBinding;
import es.unex.parsiapp.listadapter.ListAdapterFolder;
import es.unex.parsiapp.model.Carpeta;
import es.unex.parsiapp.ui.FolderContentActivity;
import es.unex.parsiapp.util.AppContainer;

public class FolderFragment extends Fragment {

    private FolderViewModel mViewModel;
    private FragmentFolderBinding binding; // Binding
    private View root;
    private ListAdapterFolder mAdapter;

    // --- Métodos de Callback ---

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFolderBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        AppContainer appContainer = ((MyApplication) requireActivity().getApplication()).appContainer;
        mViewModel = new ViewModelProvider(requireActivity(), (ViewModelProvider.Factory) appContainer.Ffactory).get(FolderViewModel.class);

        mAdapter = new ListAdapterFolder(mViewModel.getFolders().getValue(),root.getContext() , new ListAdapterFolder.OnItemClickListener() {
            @Override
            public void onItemClick(Carpeta item) {
                moveToFolderContent(item, root);
            }
        });

        mViewModel.getFolders().observe(getViewLifecycleOwner(), carpetas -> {
            mAdapter.swap(carpetas);
        });

        showFolders(root);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    // --- Otros métodos ---

    // Muestra y actualiza las carpetas
    public void showFolders(View root){
        RecyclerView recyclerView = root.findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(mAdapter);
    }

    // Ver los posts guardados en una carpeta
    public void moveToFolderContent(Carpeta item, View root){
        Intent intent = new Intent(root.getContext(), FolderContentActivity.class);
        intent.putExtra("folderContent", item);
        startActivity(intent);
    }
}