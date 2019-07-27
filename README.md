### 建库脚本
```
use rbac
db.createUser({
  user: "rbac",
  pwd: "rbac123",
  roles: [ { role: "readWrite", db: "rbac" } ]
})
db.auth('rbac','rbac123')
```
