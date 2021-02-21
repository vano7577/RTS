x = signal_generator(12, 1024);
x1 = signal_generator(120, 1024);
MD.plot_signal(x);
MD.M(x);
MD.D(x)
a=MD.On();
MD.Mx(2048);
[c,lags] = MD.corr (x);
[c1,lags1] = MD.two_corr (x, x1);
MD.plot_corr(lags,c,'автокорреляционная');
MD.plot_corr(lags1,c1,'взаимно-корреляционная');
t= MD.time_f (@ ()MD.corr (x),'автокорреляционной');
t1= MD.time_f (@ ()MD.two_corr (x, x1),'взаимно-корреляционной');
MD.difference_time (t,t1,'автокорреляционной','взаимно-корреляционной');