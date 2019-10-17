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

### [前端工程](https://github.com/PanJiaChen/vue-element-admin/blob/master/README.zh-CN.md)

#### 调整步骤

- 1. .env.development
```
VUE_APP_BASE_API = 'http://localhost:8080'
```

- 2. src/api/user.js
``` javascript
url: '/user/login'
```
改为
``` javascript
url: '/auth'
```

- 3. src/utils/request.js
``` javascript
config.headers['X-Token'] = getToken()
```
改为
``` javascript
config.headers['Authorization'] = 'Bearer ' + getToken()
```