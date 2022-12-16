package es.unex.parsiapp.ui.columns;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.unex.parsiapp.MyApplication;
import es.unex.parsiapp.R;
import es.unex.parsiapp.databinding.FragmentColumnasBinding;
import es.unex.parsiapp.listadapter.ListAdapterColumna;
import es.unex.parsiapp.model.Columna;
import es.unex.parsiapp.util.AppContainer;

public class ColumnasFragment extends Fragment {

    private ColumnasViewModel mViewModel;
    private FragmentColumnasBinding binding;
    private View root;
    private ListAdapterColumna mAdapter;

    // --- Métodos de Callback ---
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentColumnasBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        AppContainer appContainer = ((MyApplication) requireActivity().getApplication()).appContainer;
        mViewModel = new ViewModelProvider(requireActivity(), (ViewModelProvider.Factory) appContainer.factory).get(ColumnasViewModel.class);

        mAdapter = new ListAdapterColumna(mViewModel.getColumns().getValue(), root.getContext(), new ListAdapterColumna.OnItemClickListener() {
            @Override
            public void onItemClick(Columna item) {
                item.setColumnaActual(true);
                mViewModel.setColumnaActual(item);
                showColumnas(root);
                Toast.makeText(getContext(), "Se ha cambiado la columna actual a " + item.getNombre(), Toast.LENGTH_SHORT).show();
            }
        });

        mViewModel.getColumns().observe(getViewLifecycleOwner(), columnas -> {
            mAdapter.swap(columnas);
        });

        showColumnas(root);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // --- Otros métodos ---

    // Muestra y actualiza las columnas
    public void showColumnas(View root){
        RecyclerView recyclerView = root.findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(mAdapter);
    }

}