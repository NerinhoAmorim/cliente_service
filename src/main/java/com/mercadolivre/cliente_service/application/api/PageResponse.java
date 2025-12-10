package com.mercadolivre.cliente_service.application.api;

import org.springframework.data.domain.Page;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class PageResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final List<T> content;
	private final int page;
	private final int size;
	private final long totalElements;
	private final int totalPages;
	private final boolean last;

	public PageResponse(Page<T> page) {
		this.content = page.getContent();
		this.page = page.getNumber();
		this.size = page.getSize();
		this.totalElements = page.getTotalElements();
		this.totalPages = page.getTotalPages();
		this.last = page.isLast();
	}
}
