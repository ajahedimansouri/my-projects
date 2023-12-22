package ws7.application.controller;

import java.net.URL;

import javafx.fxml.FXMLLoader;

public abstract class Controller<T,R,M> {

	protected M model;
	protected static FXMLLoader loader;
	
	public static<T> T setupScene(Class<?> clazz, Object thiz)
	{
		T root = null;
		
		try
		{
			String name =   clazz.getName().substring(clazz.getName().lastIndexOf(".")+1).replace("Controller", ".fxml");
			URL view = thiz.getClass().getResource(name);
			loader = new FXMLLoader(view);
			root = loader.load();
		} catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return root;
	}
	
	public static <R> R getController()
	{
		return (R)loader.getController();
	}
	
	
	
}
