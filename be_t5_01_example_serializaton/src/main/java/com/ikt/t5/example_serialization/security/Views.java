package com.ikt.t5.example_serialization.security;

public class Views {

	public static class Public {}
	public static class Private extends Public {}
	public static class Admin extends Private {}
}
