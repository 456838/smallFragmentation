package com.salton123.appxmly.fm;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.salton123.appxmly.R;
import com.salton123.appxmly.model.ApiPair;
import com.salton123.base.AdapterBase;
import com.salton123.base.BaseSupportFragment;
import com.salton123.base.ViewHolder;
import com.salton123.event.StartBrotherEvent;
import com.salton123.util.EventUtil;

import java.util.Arrays;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/21 15:32
 * Time: 15:32
 * Description:
 */
public class ApiTestFragment extends BaseSupportFragment {
    private ApiPair apiArr[] = new ApiPair[]{
            new ApiPair("批量获取专辑列表", "getBatch"), new ApiPair("获取喜马拉雅内容分类", "getCategories")
            ,new ApiPair("获取专辑标签或者声音标签","getTags")
            ,new ApiPair("根据分类和标签获取某个分类某个标签下的专辑列表（最火/最新/最多播放）","getAlbumList")
            ,new ApiPair("专辑浏览，根据专辑ID获取专辑下的声音列表","getTracksTest")
            ,new ApiPair("搜索专辑","getSearchedAlbumsTest")
    };

    ApiAdapter mApiAdapter;
    ListView lv_api;

    @Override
    public int GetLayout() {
        return R.layout.fm_api_test;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        mApiAdapter = new ApiAdapter(_mActivity);
    }

    @Override
    public void InitViewAndData() {
        lv_api = f(R.id.lv_api);
        lv_api.setAdapter(mApiAdapter);
        mApiAdapter.AddAll(Arrays.asList(apiArr));
    }

    @Override
    public void InitListener() {
        lv_api.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventUtil.sendEvent(new StartBrotherEvent(BaseSupportFragment.newInstance(ApiViewFragment.class, mApiAdapter.getItem(position))));
            }
        });
    }

    class ApiAdapter extends AdapterBase<ApiPair> {

        public ApiAdapter(Context pContext) {
            super(pContext);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = GetLayoutInflater().inflate(R.layout.adapter_api_item, null);
            }
            TextView tv_name = ViewHolder.get(convertView, R.id.tv_name);
            tv_name.setText(getItem(position).first);
            return convertView;
        }
    }
}
