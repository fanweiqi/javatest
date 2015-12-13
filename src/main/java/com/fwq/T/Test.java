package com.fwq.T;

import java.util.ArrayList;
import java.util.List;

public class Test {
	public static void main(String[] args) {
		List<? super Number > data=new ArrayList();
		data.add(1);
	}

}
