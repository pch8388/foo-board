package me.study.foostudy.utils;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.*;

import java.util.function.Consumer;

import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.PathParametersSnippet;
import org.springframework.test.web.reactive.server.EntityExchangeResult;

public class DocumentationUtil {
	public static <T> Consumer<EntityExchangeResult<T>> getDocument(String identifier,
		PathParametersSnippet parametersSnippet) {
		return document(identifier, parametersSnippet);
	}

	public static <T> Consumer<EntityExchangeResult<T>> getDocument(String identifier,
		ResponseFieldsSnippet responseSnippet) {
		return document(identifier,
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			responseSnippet);
	}

	public static <T> Consumer<EntityExchangeResult<T>> getDocument(String identifier,
		PathParametersSnippet parametersSnippet,
		ResponseFieldsSnippet responseSnippet) {
		return document(identifier,
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			parametersSnippet, responseSnippet);
	}

	public static <T> Consumer<EntityExchangeResult<T>> getDocument(String identifier,
		RequestFieldsSnippet requestSnippet,
		ResponseFieldsSnippet responseSnippet) {
		return document(identifier,
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			requestSnippet, responseSnippet);
	}

	public static <T> Consumer<EntityExchangeResult<T>> getDocument(String identifier,
		PathParametersSnippet parametersSnippet,
		RequestFieldsSnippet requestSnippet,
		ResponseFieldsSnippet responseSnippet) {
		return document(identifier,
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			parametersSnippet, requestSnippet, responseSnippet);
	}
}
