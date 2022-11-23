package es.unex.parsiapp.ui.columns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ColumnasViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public ColumnasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is columnas fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
