/*
 *     Android基础开发个人积累、沉淀、封装、整理共通
 *     Copyright (c) 2017. 曾繁添 <zftlive@163.com>
 *     Github：https://github.com/zengfantian || http://git.oschina.net/zftlive
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */

package com.zftlive.android.sample.animation.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zftlive.android.R;

@SuppressWarnings("unused")
public class TestFragment extends Fragment {
	private static final String KEY_CONTENT = "TestFragment:Position";
	protected static final int[] CONTENT = new int[] {
			R.drawable.london,
			R.drawable.ny,
			R.drawable.ny_light,
			R.drawable.tokyo,
			R.drawable.london
			
	};
	private int mPosition;

	public static TestFragment newInstance(int position) {
		TestFragment fragment = new TestFragment();
		fragment.mPosition = position;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if ((savedInstanceState != null)
				&& savedInstanceState.containsKey(KEY_CONTENT)) {
			mPosition = savedInstanceState.getInt(KEY_CONTENT);
		}
		View root = inflater
				.inflate(R.layout.sample_fragment_layout_vp_anim, container, false);
		root.setBackgroundResource(CONTENT[mPosition]);
		return root;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(KEY_CONTENT, mPosition);
	}
}
