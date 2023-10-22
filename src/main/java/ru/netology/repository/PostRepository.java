package ru.netology.repository;

import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {
    private final AtomicLong postId;
    private final Map<Long, Post> postsMap;

    public PostRepository() {
        postId = new AtomicLong(0);
        postsMap = new ConcurrentHashMap<>();
    }

    public List<Post> all() {
        return new ArrayList<>(postsMap.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(postsMap.get(id));
    }

    public Post save(Post post) {
        long specificID = post.getId();
        if (specificID > 0 && postsMap.containsKey(specificID)) {
            postsMap.replace(specificID, post);
        } else {
            long newIdPost = specificID == 0 ? postId.incrementAndGet() : specificID;
            post.setId(newIdPost);
            postsMap.put(newIdPost, post);
        }
        return post;
    }

    public void removeById(long id) {
        postsMap.remove(id);
    }
}
