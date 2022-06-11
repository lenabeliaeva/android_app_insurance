package com.example.insurance.TopLevelScreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.insurance.PoliceActivity;
import com.example.insurance.QuestionnaireActivity;
import com.example.insurance.R;
import com.example.insurance.SignInActivity;
import com.example.insurance.UserActivity;
import com.example.insurance.pojo.Police;
import com.example.insurance.pojo.User;
import java.util.List;

public class MyPoliciesFragment extends ListFragment {
    User user;
    Button signInButton;
    Button signUpButton;
    Button questButton;
    TextView textView;
    Strategy strategy;
    List<Police> policies;
    String[] policiesArray;

    public MyPoliciesFragment() {
        // Required empty public constructor
    }

    public MyPoliciesFragment(User u) {
        user = u;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout;
        if (user.isConnected()) {
            layout = inflater.inflate(R.layout.fragment_my_policies, container, false);
            strategy = new ShowPolicesStrategy();
            MyPoliciesPresenter presenter = new MyPoliciesPresenter(this);
            presenter.tryGetUserPoliciesList(user.getId());
        } else {
            layout = inflater.inflate(R.layout.fragment_my_policies_not, container, false);
            strategy = new SignInStrategy();
        }
        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        strategy.execute(view);
        AdapterView.OnItemClickListener itemClickListener = (parent, view1, position, id) -> {
            Intent intent = new Intent(getActivity(), PoliceActivity.class);
            intent.putExtra(PoliceActivity.EXTRA_POLICE, policies.get(position));
            intent.putExtra(PoliceActivity.EXTRA_USER, user);
            startActivity(intent);
        };
        getListView().setOnItemClickListener(itemClickListener);
    }

    class ShowPolicesStrategy implements Strategy {
        @Override
        public void execute(View view) {
            questButton = view.findViewById(R.id.quest_btn);
            questButton.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), QuestionnaireActivity.class);
                intent.putExtra(UserActivity.EXTRA_USER, user);
                startActivity(intent);
            });
            textView = view.findViewById(R.id.my_p_l);
        }
    }

    public void setPoliciesList(List<Police> policies) {
//        if (policies.size() < 1) {
//            textView.setText("Пока ни одного приобретённого полиса");
//        } else {
        Police mock = new Police();
        mock.setTypeOfObject(22);
            this.policies = policies;
            policies.add(mock);
            policiesArray = new String[policies.size()];
            Police police;
            for (int i = 0; i < policies.size(); ++i) {
                police = policies.get(i);
                policiesArray[i] = police.toString();
            }
            ArrayAdapter<String> policiesAdapter = new ArrayAdapter<>(
                    requireActivity(),
                    R.layout.category_list_item,
                    R.id.cat_list_item,
                    policiesArray
            );
            setListAdapter(policiesAdapter);
//        }
    }

    class SignInStrategy implements Strategy {
        @Override
        public void execute(View layout) {
            signInButton = layout.findViewById(R.id.button1);
            signUpButton = layout.findViewById(R.id.button2);
            signInButton.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                intent.putExtra(SignInActivity.EXTRA_USER, user);
                startActivity(intent);
            });
            signUpButton.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), UserActivity.class);
                intent.putExtra(UserActivity.EXTRA_USER, user);
                startActivity(intent);
            });
        }
    }
}
