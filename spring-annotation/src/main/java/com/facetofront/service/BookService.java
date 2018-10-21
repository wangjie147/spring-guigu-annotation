package com.facetofront.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.facetofront.dao.BookDao;

@Service
public class BookService {
	  
	  @Autowired(required=false)  
	  @Qualifier("bookDao2")
	  private BookDao bookDao;
	  
	  public void test(){
		  System.out.println(bookDao);
	  }

		@Override
		public String toString() {
			return "BookService [bookDao=" + bookDao + "]";
		}
	  
	  
	  

}
