package com.zk.model.act;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.inveno.base.BaseModel;

@Entity
@Table(name = "t_zk_act_detail")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ActivityDetail extends BaseModel{

	    /**
	 * 
	 */
	private static final long serialVersionUID = -655464726685237511L;
		/***
	     * 应用ID,主键
	     */
		@Id
		@Column(name = "detail_id")
		@GeneratedValue(generator = "activityDetailGenerate", strategy = GenerationType.IDENTITY)
		@GenericGenerator(name = "activityDetailGenerate", strategy = "native")
		private String id;

		
		@Column(name="act_id")
		private String actId;// 活动id
		
		@Column(name="act_detail")
		private String actDetail;// 活动详情
		
		@Column(name="prd_name")
		private String prdName;//商品名称
		
		@Column(name="prd_model")
		private String prdModel;//商品型号
		
		@Column(name="city")
		private String city;//   活动所在市
		
		@Column(name="undertaker")
		private String undertaker;//      活动商
		
		@Column(name = "book_time")
		@Temporal(TemporalType.TIMESTAMP)
		private Date bookTime;//   预订时间
		
		@Column(name = "create_time")
		@Temporal(TemporalType.TIMESTAMP)
		private Date createTime;//创建时间             

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getActId() {
			return actId;
		}

		public void setActId(String actId) {
			this.actId = actId;
		}

		public String getActDetail() {
			return actDetail;
		}

		public void setActDetail(String actDetail) {
			this.actDetail = actDetail;
		}

		public String getPrdName() {
			return prdName;
		}

		public void setPrdName(String prdName) {
			this.prdName = prdName;
		}


		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}


		public Date getBookTime() {
			return bookTime;
		}

		public void setBookTime(Date bookTime) {
			this.bookTime = bookTime;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public String getPrdModel() {
			return prdModel;
		}

		public void setPrdModel(String prdModel) {
			this.prdModel = prdModel;
		}

		public String getUndertaker() {
			return undertaker;
		}

		public void setUndertaker(String undertaker) {
			this.undertaker = undertaker;
		}
}
