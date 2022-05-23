package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.BlogInfo;
import com.example.demo.model.UserInfo;

@Repository
public interface BlogInfoRepository extends JpaRepository<BlogInfo,Long>{

	BlogInfo findById(long id);

	
	
}
