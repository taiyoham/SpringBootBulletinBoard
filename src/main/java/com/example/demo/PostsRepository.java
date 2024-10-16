package com.example.demo;

import com.example.demo.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Integer>{
	public Posts findById(int id);
	public void deleteById(int id);
}
