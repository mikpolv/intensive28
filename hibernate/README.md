### HomeWork JDBC
Создается три сущности:
<br>Product
<br>Brand
<br>Supplier
<br>Для всех релиозваны CRUD операции. 
<br>Для демоснтрации реализованы следующие endpoints:

<br>/product с параметром action:
<br> - find(param: id)
<br> - create_resistor(params: name, brand_id, part_number, resistance, voltage)
<br> - remove(param: id)
<br> - update_resistor(params: id, name, brand_id, part_number, resistance, voltage)

<br>/brands - возвращает список всех Brand:

<br>/suppliers c параметром id- возвращает список Supplier для Product id.

Задания оп N+1 и LazyInitializationException реализованы в HibernateProductDao.java