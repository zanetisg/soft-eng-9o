CREATE TABLE IF NOT EXISTS `users` (
	`id` integer primary key autoincrement,
	`username` text unique not null,
	`password` text not null,
	`is_fm` integer(1) not null,
	`is_hr` integer(1) not null
);

INSERT INTO
	`users` (`username`, `password`, `is_fm`, `is_hr`)
VALUES
	('foreman', 'foreman', 1, 0),
	('hr', 'hr', 0, 1);

CREATE TABLE IF NOT EXISTS `employees` (
	`id` integer primary key autoincrement,
	`firstname` text not null,
	`lastname` text not null,
	`fathername` text not null,
	`mothername` text not null,
	`birthdate` integer not null,
	`email` text not null,
	`country` text not null,
	`city` text not null,
	`area` text not null,
	`zip` integer(5) not null,
	`address` text not null,
	`phone` integer not null,
	`mobile` integer not null,
	`salary` double not null
);

CREATE TABLE IF NOT EXISTS `comments` (
	`id` integer primary key autoincrement,
	`user_id` integer not null,
	`employee_id` integer not null,
	`timestamp` integer not null,
	`comment` text not null
);