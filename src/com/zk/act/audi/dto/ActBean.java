package com.zk.act.audi.dto;

import com.zk.common.baseclass.BaseFormBean;
import com.zk.model.act.Activity;
import com.zk.model.act.ActivityDetail;

public class ActBean extends BaseFormBean{
  private Activity activity;
  private ActivityDetail actDetail;

public Activity getActivity() {
	return activity;
}

public void setActivity(Activity activity) {
	this.activity = activity;
}

public ActivityDetail getActDetail() {
	return actDetail;
}

public void setActDetail(ActivityDetail actDetail) {
	this.actDetail = actDetail;
}
}
