# JavaMySQL  
## 概要  
JavaでMySQLにアクセスするためのアプリ
### 機能説明  
* INSERT  
INSERTメソッドに`HashMap<String , Object>`を渡す.  

* SELECT  
SELECTメソッドに`ArrayList<String>`で欲しいデータのcolumn情報を渡す  
結果は,`ArrayList<HashMap<String , Object>>`に入力される
* DELETE  
現状では,全件削除のDeleteAllのみ実装　　

* UPDATE  
未実装  
#### 補足  
DBにアクセスするための情報(ユーザー名やパスワード)は外部のJSONファイルに記述  
JSONパースに[JSONIC](http://jsonic.osdn.jp/)を使用  
