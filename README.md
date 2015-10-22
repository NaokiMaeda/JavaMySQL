# JavaMySQL  
## 概要  
JavaでMySQLにアクセスするためのアプリ
### 機能説明  
* INSERT  
INSERTメソッドに`HashMap<String , Object>`を渡す.  

* SELECT  
SELECTメソッドに実行したいSQL構文を入力  
結果は,`Arraylist<HashMap>`に入力される
* DELETE  
現状では,全件削除のDeleteAllのみ実装　　

* UPDATE  
未実装  
#### 補足  
DBにアクセスするための情報(ユーザー名やパスワード)は外部のJSONファイルに記述  
JSONパースに[JSONIC](http://jsonic.osdn.jp/)を使用  
