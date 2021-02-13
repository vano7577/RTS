x = signal_generator(12);
plot(x);
title('Случайный сигнал')
xlabel('t')
ylabel('x(t)')
disp('Лаб №1')

M=mean(x); 
fprintf('Математическое ожидание\n M = %f\n',M);
Dx=var(x);
fprintf('Дисперсия\n D = %f\n',Dx);

a= zeros(2,120);
for n = 1:120
    y= @() signal_generator(n);
    a(1,n) = n;
    a(2,n) = timeit(y);
end
figure
plot(a(2,:));
title('Сложность алгоритма')
xlabel('n')
ylabel('O(n)')

writematrix(a,'O.csv');
x1 = signal_generator(120);
 
[c,lags] = xcorr(x,'normalized');
figure
plot(lags,c)
title('Нормированная автокорреляционная функция')
xlabel('τ')
ylabel('Ψ(τ)')
[c1,lags1] = xcorr(x,x1,'normalized');
figure
plot(lags1,c1)
title('Нормированная взаимно-корреляционная функция')
xlabel('τ')
ylabel('Ψ(τ)')