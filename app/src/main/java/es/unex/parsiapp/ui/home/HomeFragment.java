package es.unex.parsiapp.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import es.unex.parsiapp.MyApplication;
import es.unex.parsiapp.R;
import es.unex.parsiapp.databinding.FragmentHomeBinding;
import es.unex.parsiapp.listadapter.ListAdapterPost;
import es.unex.parsiapp.model.Post;
import es.unex.parsiapp.ui.TweetDetailsActivity;
import es.unex.parsiapp.util.AppContainer;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding; // Binding
    private View rootV;
    private SwipeRefreshLayout refresh;
    private ListAdapterPost mAdapter;
    private HomeViewModel mViewModel;
    private RecyclerView mRecyclerView;

    // --- Métodos de Callback ---

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Obtener view
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.rootV = root;

        AppContainer appContainer = ((MyApplication) requireActivity().getApplication()).appContainer;
        mViewModel = new ViewModelProvider(requireActivity(), (ViewModelProvider.Factory) appContainer.Hfactory).get(HomeViewModel.class);

        mAdapter = new ListAdapterPost(mViewModel.getPosts().getValue(), rootV.getContext(), new ListAdapterPost.OnItemClickListener() {
            @Override
            public void onItemClick(Post item) {
                showPost(item, rootV);
            }
        });

        mViewModel.getPosts().observe(getViewLifecycleOwner(), posts -> {
            mAdapter.swap(posts);
        });

        mRecyclerView = rootV.findViewById(R.id.listRecyclerView);

        showTweetsFromColumna(root);

        // Refresh
        refresh = (SwipeRefreshLayout) root.findViewById(R.id.swipeRefreshLayout);
        refresh.setColorSchemeResources(R.color.white);
        refresh.setProgressBackgroundColorSchemeResource(R.color.blueParsi);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String max_posts = sharedPreferences.getString("max_posts", "20");
                mViewModel.setColumna(max_posts);
                refresh.setRefreshing(false);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // --- Otros métodos ---

    // Muestra los tweets realizando una llamada a la API
    public void showTweetsFromColumna(View root){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String max_posts = sharedPreferences.getString("max_posts", "20");

        mViewModel.setColumna(max_posts);
        TextView t = (TextView) root.findViewById(R.id.addColumn);
        t.setVisibility(View.INVISIBLE);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(rootV.getContext()));
        mRecyclerView.setAdapter(mAdapter);
    }

    public void showPost(Post item, View root){
        Intent intent = new Intent(root.getContext(), TweetDetailsActivity.class);
        intent.putExtra("Post", item);
        intent.putExtra("Saved", 0);
        startActivity(intent);
    }

}