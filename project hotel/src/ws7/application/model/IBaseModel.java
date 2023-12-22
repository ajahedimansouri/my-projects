package ws7.application.model;

import java.util.List;

public interface IBaseModel<T,KEY> {
   void add(T t);
   void delete(T t);
   void update(T t);
   T    load(KEY id);
   List<T> list();
}
