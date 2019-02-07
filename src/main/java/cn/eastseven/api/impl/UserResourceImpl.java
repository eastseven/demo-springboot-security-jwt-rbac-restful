package cn.eastseven.api.impl;

import cn.eastseven.api.UserResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author eastseven
 */
@RestController
@RequestMapping(value = "users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserResourceImpl implements UserResource {

    @GetMapping
    @Override
    public Object list() {
        return null;
    }

    @GetMapping("/{id}")
    @Override
    public Object get(@PathVariable String id) {
        return null;
    }

    @PostMapping
    @Override
    public Object add(@RequestBody Object request) {
        return null;
    }

    @PutMapping
    @Override
    public Object modify(@RequestBody Object request) {
        return null;
    }

    @DeleteMapping("/{id}")
    @Override
    public Object remove(@PathVariable String id) {
        return null;
    }
}
