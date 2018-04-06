package cn.goldlone.safe.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import cn.goldlone.safe.R;
import cn.goldlone.safe.view.activity.BuyActivity;
import cn.goldlone.safe.view.activity.CityActivity;
import cn.goldlone.safe.view.activity.LoginActivity;
import cn.goldlone.safe.view.activity.ManagerFamilyActivity;
import cn.goldlone.safe.view.activity.UpdateNickNameActivity;
import cn.goldlone.safe.view.activity.UpdatePasswordActivity;


/**
 * 个人中心
 * Created by chenpengfei on 2017/3/21.
 */
public class MineFragment extends Fragment implements View.OnClickListener {

    private View rootView;
    private TextView tv_mine_buy;
    private TextView tv_mine_city;
    private TextView tv_mine_manager_family;
    private TextView tv_mine_update_nick_name;
    private TextView tv_mine_update_password;
    private TextView tv_mine_logout;
    private TextView tv_mine_exit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_mine, container, false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        tv_mine_buy = (TextView) rootView.findViewById(R.id.tv_mine_buy);
        tv_mine_city = (TextView) rootView.findViewById(R.id.tv_mine_city);
        tv_mine_manager_family = (TextView) rootView.findViewById(R.id.tv_mine_manager_family);
        tv_mine_update_nick_name = (TextView) rootView.findViewById(R.id.tv_mine_update_nick_name);
        tv_mine_update_password = (TextView) rootView.findViewById(R.id.tv_mine_update_password);
        tv_mine_logout = (TextView) rootView.findViewById(R.id.tv_mine_logout);
        tv_mine_exit = (TextView) rootView.findViewById(R.id.tv_mine_exit);

        tv_mine_buy.setOnClickListener(this);
        tv_mine_city.setOnClickListener(this);
        tv_mine_manager_family.setOnClickListener(this);
        tv_mine_update_nick_name.setOnClickListener(this);
        tv_mine_update_password.setOnClickListener(this);
        tv_mine_logout.setOnClickListener(this);
        tv_mine_exit.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_mine_buy:
                startActivity(new Intent(getActivity(), BuyActivity.class));
//                Toast.makeText(getActivity(), "购物平台", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_mine_city:
                startActivity(new Intent(getActivity(), CityActivity.class));
//                Toast.makeText(getActivity(), "同城义务服务", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_mine_manager_family:
                startActivity(new Intent(getActivity(), ManagerFamilyActivity.class));
//                Toast.makeText(getActivity(), "管理家人组", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_mine_update_nick_name:
                startActivity(new Intent(getActivity(), UpdateNickNameActivity.class));
//                Toast.makeText(getActivity(), "修改昵称", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_mine_update_password:
                startActivity(new Intent(getActivity(), UpdatePasswordActivity.class));
//                Toast.makeText(getActivity(), "修改密码", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_mine_logout:
                getActivity().finish();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.tv_mine_exit:
                getActivity().finish();
                break;
        }
    }
}
