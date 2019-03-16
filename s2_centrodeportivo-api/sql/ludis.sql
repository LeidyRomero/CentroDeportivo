delete from SeguimientoEntity;
delete from DeportistaEntity;
delete from EjercicioEntity;
delete from EspecialistaEntity;
delete from ImplementoEntity;
delete from MaquinaEntity;
delete from ObjetivoEntity;
delete from RutinaEntity;
delete from ZonacuerpoEntity;
delete from SeguimientoEntity_MaquinaEntity;

 -- POBLAMIENTO TABLA DEPORTISTA
insert into DeportistaEntity (id, cedula, nombre, sexo, altura, presionSanguinea, peso, ritmoCardiaco, medidaPiernas, medidaCintura, medidaBrazos, fechaNacimiento) values (1, 11, 'Jessee Templeton', 1, 1.59, 80.46, 82, 265, 1.69, 1.11, 1.76, '1985-11-20 00:00:00');
insert into DeportistaEntity (id, cedula, nombre, sexo, altura, presionSanguinea, peso, ritmoCardiaco, medidaPiernas, medidaCintura, medidaBrazos, fechaNacimiento) values (2, 12, 'Brandise Beaven', 0, 1.25, 86.6, 92, 117, 1.33, 3.63, 1.9, '1985-11-20 00:00:00');
insert into DeportistaEntity (id, cedula, nombre, sexo, altura, presionSanguinea, peso, ritmoCardiaco, medidaPiernas, medidaCintura, medidaBrazos, fechaNacimiento) values (3, 13, 'Jamie Archambault', 0, 1.81, 124.72, 66, 126, 1.85, 3.84, 1.59, '1985-11-20 00:00:00');
insert into DeportistaEntity (id, cedula, nombre, sexo, altura, presionSanguinea, peso, ritmoCardiaco, medidaPiernas, medidaCintura, medidaBrazos, fechaNacimiento) values (4, 14, 'Terrie Jovanovic', 0, 1.47, 93.27, 61, 180, 1.39, 1.31, 1.29, '1985-11-20 00:00:00');
insert into DeportistaEntity (id, cedula, nombre, sexo, altura, presionSanguinea, peso, ritmoCardiaco, medidaPiernas, medidaCintura, medidaBrazos, fechaNacimiento) values (5, 15, 'Melinde De Laci', 0, 1.41, 96.26, 61, 280, 2.0, 3.64, 1.03, '1985-11-20 00:00:00');
insert into DeportistaEntity (id, cedula, nombre, sexo, altura, presionSanguinea, peso, ritmoCardiaco, medidaPiernas, medidaCintura, medidaBrazos, fechaNacimiento) values (6, 16, 'Edwina Perell', 0, 1.94, 126.24, 70, 109, 1.05, 1.88, 1.35, '1985-11-20 00:00:00');
insert into DeportistaEntity (id, cedula, nombre, sexo, altura, presionSanguinea, peso, ritmoCardiaco, medidaPiernas, medidaCintura, medidaBrazos, fechaNacimiento) values (7, 17, 'Broddie Starking', 1, 1.74, 98.19, 44, 251, 1.42, 1.55, 1.0,'1985-11-20 00:00:00');
insert into DeportistaEntity (id, cedula, nombre, sexo, altura, presionSanguinea, peso, ritmoCardiaco, medidaPiernas, medidaCintura, medidaBrazos, fechaNacimiento) values (8, 18, 'Vasili McAulay', 1, 1.83, 106.82, 29, 211, 1.89, 3.47, 1.17, '1985-11-20 00:00:00');
insert into DeportistaEntity (id, cedula, nombre, sexo, altura, presionSanguinea, peso, ritmoCardiaco, medidaPiernas, medidaCintura, medidaBrazos, fechaNacimiento) values (9, 19, 'Ingelbert Daud', 1, 1.1, 104.18, 51, 117, 1.4, 1.91, 1.77, '1985-11-20 00:00:00');
insert into DeportistaEntity (id, cedula, nombre, sexo, altura, presionSanguinea, peso, ritmoCardiaco, medidaPiernas, medidaCintura, medidaBrazos, fechaNacimiento) values (10, 20, 'Estel Pitcher', 0, 1.08, 83.28, 48, 40, 1.9, 2.36, 1.29, '1985-11-20 00:00:00');

 --POBLAMIENTO TABLA EJERCICIO
insert into ejercicioEntity (id, nombre, categoria, duracion, numeroDeSeries, descripcion, explicacion) values (1, '', 'pede venenatis non sodales sed tincidunt eu felis fusce posuere felis sed lacus morbi', 1, 1, 'pede justo lacinia eget tincidunt eget tempus vel pede morbi porttitor', 'dapibus dolor vel est donec odio justo sollicitudin ut suscipit a feugiat et eros vestibulum ac');
insert into ejercicioEntity (id, nombre, categoria, duracion, numeroDeSeries, descripcion, explicacion) values (2, '', 'nulla tellus in sagittis dui vel nisl duis ac nibh fusce lacus purus aliquet at', 2, 2, 'nibh in quis justo maecenas rhoncus aliquam lacus morbi quis', 'blandit mi in porttitor pede justo eu massa donec dapibus duis at velit eu est congue elementum in');
insert into ejercicioEntity (id, nombre, categoria, duracion, numeroDeSeries, descripcion, explicacion) values (3, 'tempus', 'sed vestibulum sit amet cursus id turpis integer aliquet massa id lobortis convallis', 3, 3, 'elit proin risus praesent lectus vestibulum quam sapien varius ut blandit non interdum in ante', 'dui nec nisi volutpat eleifend donec ut dolor morbi vel lectus in quam fringilla rhoncus mauris enim leo rhoncus');
insert into ejercicioEntity (id, nombre, categoria, duracion, numeroDeSeries, descripcion, explicacion) values (4, 'suspendisse accumsan tortor quis turpis', 'elit ac nulla sed vel enim sit amet nunc viverra dapibus nulla', 4, 4, 'aliquet ultrices erat tortor sollicitudin mi sit amet lobortis sapien sapien non', 'maecenas ut massa quis augue luctus tincidunt nulla mollis molestie');
insert into ejercicioEntity (id, nombre, categoria, duracion, numeroDeSeries, descripcion, explicacion) values (5, 'convallis tortor risus dapibus augue vel', 'at nulla suspendisse potenti cras in purus eu magna vulputate luctus cum sociis natoque penatibus et magnis dis', 5, 5, 'orci pede venenatis non sodales sed tincidunt eu felis fusce posuere felis sed lacus morbi', 'augue luctus tincidunt nulla mollis molestie lorem quisque ut erat');
insert into ejercicioEntity (id, nombre, categoria, duracion, numeroDeSeries, descripcion, explicacion) values (6, 'tortor duis', 'scelerisque quam turpis adipiscing lorem vitae mattis nibh ligula nec', 6, 6, 'posuere cubilia curae nulla dapibus dolor vel est donec odio justo sollicitudin ut suscipit a feugiat et', 'dis parturient montes nascetur ridiculus mus etiam vel augue vestibulum rutrum');
insert into ejercicioEntity (id, nombre, categoria, duracion, numeroDeSeries, descripcion, explicacion) values (7, 'tellus semper interdum mauris ullamcorper purus', 'felis fusce posuere felis sed lacus morbi sem mauris laoreet ut rhoncus aliquet', 7, 7, 'congue vivamus metus arcu adipiscing molestie hendrerit at vulputate vitae nisl', 'convallis tortor risus dapibus augue vel accumsan tellus nisi eu orci mauris lacinia');
insert into ejercicioEntity (id, nombre, categoria, duracion, numeroDeSeries, descripcion, explicacion) values (8, '', 'mi in porttitor pede justo eu massa donec dapibus duis at velit eu est congue elementum in hac habitasse', 8, 8, 'nec euismod scelerisque quam turpis adipiscing lorem vitae mattis nibh ligula nec sem duis aliquam convallis nunc proin', 'iaculis congue vivamus metus arcu adipiscing molestie hendrerit at vulputate vitae nisl aenean lectus pellentesque');
insert into ejercicioEntity (id, nombre, categoria, duracion, numeroDeSeries, descripcion, explicacion) values (9, 'libero convallis eget eleifend luctus ultricies eu', 'tellus in sagittis dui vel nisl duis ac nibh fusce lacus purus aliquet at feugiat non pretium quis', 9, 9, 'et tempus semper est quam pharetra magna ac consequat metus sapien ut nunc', 'nulla neque libero convallis eget eleifend luctus ultricies eu nibh quisque id justo sit');
insert into ejercicioEntity (id, nombre, categoria, duracion, numeroDeSeries, descripcion, explicacion) values (10, 'quam turpis adipiscing', 'magna vulputate luctus cum sociis natoque penatibus et magnis dis parturient montes nascetur ridiculus mus vivamus vestibulum sagittis sapien cum', 10, 10, 'duis consequat dui nec nisi volutpat eleifend donec ut dolor morbi vel lectus in quam fringilla rhoncus mauris enim', 'aenean auctor gravida sem praesent id massa id nisl venenatis lacinia aenean sit amet justo morbi');

 --POBLAMIENTO TABLA ESPECIALISTA
insert into especialistaEntity (id, Especialidad, nombre, Cedula) values (1, 'Farrel', 'Farrel Upstone', 99999999);
insert into especialistaEntity (id, Especialidad, nombre, Cedula) values (2, 'Bellina', 'Bellina Addlestone', 99999999);
insert into especialistaEntity (id, Especialidad, nombre, Cedula) values (3, 'Willie', 'Willie Stave', 99999999);
insert into especialistaEntity (id, Especialidad, nombre, Cedula) values (4, 'Tilda', 'Tilda Ianne', 100000000);
insert into especialistaEntity (id, Especialidad, nombre, Cedula) values (5, 'Elsi', 'Elsi Purches', 100000000);
insert into especialistaEntity (id, Especialidad, nombre, Cedula) values (6, 'Lauryn', 'Lauryn Larkin', 100000000);
insert into especialistaEntity (id, Especialidad, nombre, Cedula) values (7, 'Angy', 'Angy Drissell', 100000000);
insert into especialistaEntity (id, Especialidad, nombre, Cedula) values (8, 'Lawrence', 'Lawrence Bothie', 99999999);
insert into especialistaEntity (id, Especialidad, nombre, Cedula) values (9, 'Whit', 'Whit Ford', 100000000);
insert into especialistaEntity (id, Especialidad, nombre, Cedula) values (10, 'Alberto', 'Alberto Chiechio', 99999999);

 --POBLAMIENTO TABLA IMPLEMENTO
insert into ImplementoEntity (id, nombre, cantidadExistencias) values (1, 'dui vel', 32);
insert into ImplementoEntity (id, nombre, cantidadExistencias) values (2, 'ultrices', 32);
insert into ImplementoEntity (id, nombre, cantidadExistencias) values (3, 'aliquet', 15);
insert into ImplementoEntity (id, nombre, cantidadExistencias) values (4, 'ante', 32);
insert into ImplementoEntity (id, nombre, cantidadExistencias) values (5, 'vel', 27);
insert into ImplementoEntity (id, nombre, cantidadExistencias) values (6, 'vulputate justo', 33);
insert into ImplementoEntity (id, nombre, cantidadExistencias) values (7, 'blandit mi', 25);
insert into ImplementoEntity (id, nombre, cantidadExistencias) values (8, 'faucibus accumsan', 16);
insert into ImplementoEntity (id, nombre, cantidadExistencias) values (9, 'vivamus vestibulum', 32);
insert into ImplementoEntity (id, nombre, cantidadExistencias) values (10, 'eu felis', 10);

 --POBLAMIENTO TABLA ZONA_CUERPO
insert into zonaCuerpoEntity (id, nombre) values (1, 'dis parturient');
insert into zonaCuerpoEntity (id, nombre) values (2, '');
insert into zonaCuerpoEntity (id, nombre) values (3, 'est phasellus');
insert into zonaCuerpoEntity (id, nombre) values (4, 'ornare consequat lectus in est risus');
insert into zonaCuerpoEntity (id, nombre) values (5, 'amet sapien dignissim vestibulum vestibulum');
insert into zonaCuerpoEntity (id, nombre) values (6, 'eu magna vulputate luctus');
insert into zonaCuerpoEntity (id, nombre) values (7, 'et');
insert into zonaCuerpoEntity (id, nombre) values (8, 'luctus ultricies eu nibh quisque');
insert into zonaCuerpoEntity (id, nombre) values (9, 'sed');
insert into zonaCuerpoEntity (id, nombre) values (10, 'integer aliquet massa id lobortis convallis tortor');

 --POBLAMIENTO TABLA OBJETIVO
insert into ObjetivoEntity (id, descripcion, cumplio, fechaLimite) values (1, 'Right-sized context-sensitive encryption', 0, '1985-11-20 00:00:00');
insert into ObjetivoEntity (id, descripcion, cumplio, fechaLimite) values (2, 'Vision-oriented contextually-based function', 1, '1985-11-20 00:00:00');
insert into ObjetivoEntity (id, descripcion, cumplio, fechaLimite) values (3, 'Integrated human-resource solution', 0, '1985-11-20 00:00:00');
insert into ObjetivoEntity (id, descripcion, cumplio, fechaLimite) values (4, 'Implemented next generation ability', 1,'1985-11-20 00:00:00');
insert into ObjetivoEntity (id, descripcion, cumplio, fechaLimite) values (5, 'Reverse-engineered 24 hour migration', 1, '1985-11-20 00:00:00');
insert into ObjetivoEntity (id, descripcion, cumplio, fechaLimite) values (6, 'Extended foreground solution', 0, '1985-11-20 00:00:00');
insert into ObjetivoEntity (id, descripcion, cumplio, fechaLimite) values (7, 'Front-line radical open architecture', 1, '1985-11-20 00:00:00');
insert into ObjetivoEntity (id, descripcion, cumplio, fechaLimite) values (8, 'Public-key mobile Graphical User Interface', 0, '1985-11-20 00:00:00');
insert into ObjetivoEntity (id, descripcion, cumplio, fechaLimite) values (9, 'Persevering object-oriented access', 0, '1985-11-20 00:00:00');
insert into ObjetivoEntity (id, descripcion, cumplio, fechaLimite) values (10, 'Function-based maximized project', 0, '1985-11-20 00:00:00');

 --POBLAMIENTO TABLA MAQUINA
insert into MAQUINAENTITY (id, nombre, referencia, calorias, tiempo, velocidad, velocidadPromedio) values (1, 'Mitsubishi', '12334', 50.73, 48.04, 87.58, 13.26);
insert into MAQUINAENTITY (id, nombre, referencia, calorias, tiempo, velocidad, velocidadPromedio) values (2, 'Pontiac', '4542', 8.81, 70.22, 83.68, 8.61);
insert into MAQUINAENTITY (id, nombre, referencia, calorias, tiempo, velocidad, velocidadPromedio) values (3, 'Toyota', '3526', 72.45, 8.33, 53.09, 3.7);
insert into MAQUINAENTITY (id, nombre, referencia, calorias, tiempo, velocidad, velocidadPromedio) values (4, 'Saturn', '325345', 95.11, 13.93, 18.71, 96.0);
insert into MAQUINAENTITY (id, nombre, referencia, calorias, tiempo, velocidad, velocidadPromedio) values (5, 'Lexus', '45234', 99.47, 13.48, 25.17, 10.01);
insert into MAQUINAENTITY (id, nombre, referencia, calorias, tiempo, velocidad, velocidadPromedio) values (6, 'Chevrolet', '234553', 87.85, 48.42, 82.91, 50.0);
insert into MAQUINAENTITY (id, nombre, referencia, calorias, tiempo, velocidad, velocidadPromedio) values (7, 'Volkswagen', '2353', 3.1, 90.9, 76.94, 9.18);
insert into MAQUINAENTITY (id, nombre, referencia, calorias, tiempo, velocidad, velocidadPromedio) values (8, 'Honda', '4534', 51.36, 80.49, 34.8, 9.83);
insert into MAQUINAENTITY (id, nombre, referencia, calorias, tiempo, velocidad, velocidadPromedio) values (9, 'Land Rover', '423523', 12.5, 44.11, 35.09, 69.39);
insert into MAQUINAENTITY (id, nombre, referencia, calorias, tiempo, velocidad, velocidadPromedio) values (10, 'Toyota', '435342', 45.6, 62.33, 49.36, 69.25);



 --POBLAMIENTO TABLA RUTINA
insert into rutinaEntity (id, estadoTerminado, nombre, identificadorRutina) values (1, 1, 'Diplock', 1);
insert into rutinaEntity (id, estadoTerminado, nombre, identificadorRutina) values (2, 1, 'Farnworth', 2);
insert into rutinaEntity (id, estadoTerminado, nombre, identificadorRutina) values (3, 1, 'Banane', 3);
insert into rutinaEntity (id, estadoTerminado, nombre, identificadorRutina) values (4, 0, 'Flobert', 4);
insert into rutinaEntity (id, estadoTerminado, nombre, identificadorRutina) values (5, 1, 'Keemer', 5);
insert into rutinaEntity (id, estadoTerminado, nombre, identificadorRutina) values (6, 0, 'Yushmanov', 6);
insert into rutinaEntity (id, estadoTerminado, nombre, identificadorRutina) values (7, 0, 'Simon', 7);
insert into rutinaEntity (id, estadoTerminado, nombre, identificadorRutina) values (8, 0, 'Vallender', 8);
insert into rutinaEntity (id, estadoTerminado, nombre, identificadorRutina) values (9, 1, 'Idle', 9);
insert into rutinaEntity (id, estadoTerminado, nombre, identificadorRutina) values (10, 1, 'Marsie', 10);

 --POBLAMIENTO TABLA SEGUIMIENTO
insert into SeguimientoEntity (id, calorias, ritmocardiacopromedio, tiempo, velocidadpromedio, deportista_id) values (261, 84.5, 111.3, 33.1, 46.6, 1);
insert into SeguimientoEntity (id, calorias, ritmocardiacopromedio, tiempo, velocidadpromedio, deportista_id) values (366, 230.1, 183.2, 7.0, 122.6, 2);
insert into SeguimientoEntity (id, calorias, ritmocardiacopromedio, tiempo, velocidadpromedio, deportista_id) values (476, 181.1, 156.4, 33.8, 140.9, 3);
insert into SeguimientoEntity (id, calorias, ritmocardiacopromedio, tiempo, velocidadpromedio, deportista_id) values (468, 169.2, 161.9, 15.7, 132.2, 4);
insert into SeguimientoEntity (id, calorias, ritmocardiacopromedio, tiempo, velocidadpromedio, deportista_id) values (326, 117.5, 196.9, 8.0, 32.8, 5);
insert into SeguimientoEntity (id, calorias, ritmocardiacopromedio, tiempo, velocidadpromedio, deportista_id) values (379, 214.1, 94.2, 23.9, 119.1, 6);
insert into SeguimientoEntity (id, calorias, ritmocardiacopromedio, tiempo, velocidadpromedio, deportista_id) values (415, 91.5, 96.6, 17.1, 68.7, 7);
insert into SeguimientoEntity (id, calorias, ritmocardiacopromedio, tiempo, velocidadpromedio, deportista_id) values (421, 174.5, 136.8, 49.9, 69.6, 8);
insert into SeguimientoEntity (id, calorias, ritmocardiacopromedio, tiempo, velocidadpromedio, deportista_id) values (249, 84.6, 69.5, 21.4, 47.9, 9);
insert into SeguimientoEntity (id, calorias, ritmocardiacopromedio, tiempo, velocidadpromedio, deportista_id) values (203, 87.5, 114.9, 47.0, 163.8, 10);
