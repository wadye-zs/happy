package com.zx.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zx.family.R;

@SuppressLint("NewApi") 
public class ReCommentFragment extends Fragment implements OnClickListener{
	private View currentView;
	private LinearLayout openMenu;
	private TextView recomBtn;
	private TextView recomTle;
	private TextView recomText;
	
	

	public void setCurrentViewPararms(FrameLayout.LayoutParams layoutParams) {
		currentView.setLayoutParams(layoutParams);
	}

	public FrameLayout.LayoutParams getCurrentViewParams() {
		return (LayoutParams) currentView.getLayoutParams();
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		currentView = inflater.inflate(R.layout.slidingpane_recomment,
				container, false);
		openMenu = (LinearLayout)currentView.findViewById(R.id.linear_above_toHome);
		recomBtn =(TextView) currentView.findViewById(R.id.recom);
		recomTle =(TextView) currentView.findViewById(R.id.mail);
		recomText =(TextView) currentView.findViewById(R.id.mailtext);
		openMenu.setOnClickListener(this);
		recomBtn.setOnClickListener(this);
		return currentView;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case (R.id.linear_above_toHome):
			openMenu.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					final SlidingPaneLayout slidingPaneLayout = (SlidingPaneLayout) getActivity().findViewById(R.id.slidingpanellayout);
				    if(slidingPaneLayout.isOpen())
				    {
				    	slidingPaneLayout.closePane();
				    }
				    else
				    {
				    	slidingPaneLayout.openPane();
				    }
				}
			});
			
			break;
		case (R.id.recom):
			recomBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					recomTle.setText("");
					recomText.setText("");
					Toast.makeText(getActivity(), "反馈成功", Toast.LENGTH_SHORT).show();
				}
			});
			
			break;
		}
	}
}