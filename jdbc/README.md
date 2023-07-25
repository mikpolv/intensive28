Создается три сущности:
<br>Product
<br>Добавлены две дочерние сущности Wire и Resistor
<br>Brand
<br>Supplier
<br>Для всех релиозваны CRUD операции. 
<br>Для демоснтрации реализованы следующие endpoints:

<br>/product с параметром action:
<br> - find(param: id)
<br> - create(params: name, brand_id)
<br> - remove(param: id)
<br> - update(params: id, name, brand_id)

<br>/brands - возвращает список всех Brand:

<br>/suppliers c параметром id- возвращает список Supplier для Product id.

