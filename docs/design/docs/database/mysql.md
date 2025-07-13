# `MySQL`

## `1.Tables`

### `1.1.Scheduled`

- `riff_scheduled_app`
  - `id`
    - `app_id`
  - `app_code`
    - `${spring.application.name}`
  - `app_name`
  - `security`
    - `access_key`
      - `app_id`
    - `access_secret`
      - `app_secret`
  - `tenant`
    - `tenant`
      - `saas`
    - `platform`
      - `saas`
    - `app`
      - `boss`
      - `...`
- `riff_scheduled_task`
  - `id`
    - `task_id`
  - `--------------------------------`
  - `tenant`
  - `platform`
  - `app`
  - `--------------------------------`
  - `app_id`
  - `task_code`
  - `task_name`
  - `task_type`
    - `handler_task`
    - `glue_task`
  - `handler_name`
    - `${ioc_handler_bean_name}`
    - `hello_python`
    - `hello_shell`
    - `hello_groovy`
    - `...`
  - `declared_class`
    - `io.github.photowey.rifftest.hello.handler.HelloHandler`
    - `hello_python.py`
    - `hello_shell.sh`
    - `hello_groovy.groovy`
    - `...`
  - `method`
    - `handle - default`
  - `arguments`
    - `txt`
    - `json`
    - `yml`
    - `xml`
    - `toml`
    - `hcl`
    - `...`
- `riff_scheduled_task_client`
  - `id`
  - `task_id`
  - `${server.ip}`
  - `${server.port}`
  - `${server.protocol}`
    - `http`
    - `gRPC`
  

### `1.2.System`

- `riff_system_user`
  - `id`
    - `user_id`
  - `--------------------------------`
  - `tenant`
  - `platform`
  - `app`
  - `--------------------------------`
  - `username`
  - `password`
  - `email`
  - `mobile`
  - `avatar`
  - `twofa_enabled`
  - `twofa_secret`
  - `...`
- `riff_system_role`
  - `id`
    - `role_id`
  - `role_code`
  - `role_name`
- `riff_system_user_role_link`
  - `id`
  - `user_id`
  - `role_id`
- `riff_system_role_app_link`
  - `id`
  - `role_id`
  - `app_id`
    - `${riff_scheduled_app.id}`

### `1.3.Authentication`

- `riff_authentication_token`
  - `id`
    - `authentication`
  - `app_id`
    - `${riff_scheduled_app.id}`
  - `authentication_type`
    - `web|dashboard`
      - `username`
      - `password`
      - `1`
    - `app|client`
      - `access_key`
      - `access_secret`
      - `2`
  - `authentication_key`
    - `${riff_system_user.username}`
    - `${riff_scheduled_app.access_key}`
  - token_type
    - `bearer`
      - `web`
        - `dashboard`
    - `distributed`
      - `${riff_scheduled_app}`
  - `token`
  - `token_expire_in`
    - `3600 * 24`
    - `${long-time}`
  - `token_expire_time`
    - `datetime`
    - `${now} + ${token_expire_in} * 1000`
    - `${long-time}`
  - `refresh_token`
  - `refresh_token_expire_in`
    - `3600 * 24 * 7`
    - `${long-time}`
  - `refresh_token_expire_time`
    - `${long-time}`



## 2.`Indices`
