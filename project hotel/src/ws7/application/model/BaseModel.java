package ws7.application.model;

import ws7.application.database.JDao;


public abstract class BaseModel<T, KEY> implements IBaseModel<T, KEY> {

	 protected JDao dao;
	 protected Exception exception;
	 
	 public BaseModel()
	 {
		 this.dao = new JDao();
	 }
	 
	 public void setException(Exception ex)
	 {
		 this.exception = ex;
	 }
	 
	 public Exception getException()
	 {
		 return this.exception;
	 }
	 
	 
	 public boolean hasException()
	 {
		 return this.exception != null;
	 }
}
