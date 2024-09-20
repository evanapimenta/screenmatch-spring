package br.com.alura.screenmatch.service;

public interface IConvertData {
    <T> T convert(String json, Class<T> Tclass);
}
