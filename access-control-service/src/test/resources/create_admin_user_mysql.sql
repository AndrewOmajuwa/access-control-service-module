insert into user_key_cloak (uuid, email, first_name, last_name)
values ('c3887627-e089-47b0-8835-6cdcbf590224', 'admin@user', 'admin', 'user');
insert into profile (name)
values ('admin');
insert into user_profile (profile_id, userkeycloak_id)
values (1, 'c3887627-e089-47b0-8835-6cdcbf590224');
insert into business_function (application_name, function_name)
values ('access-control-service', 'admin');
insert into permission (name)
values ('create');
insert into business_function_permission (business_function_id, permission_id)
values (1, 1);
insert into profile_business_function_permission (business_function_permission_id, profile_id)
values (1, 1);