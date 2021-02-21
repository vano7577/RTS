function x = signal_generator(n, N)
omega0 = 2400;
x= zeros(1,N);
t = 0:N-1;
for i = 1:n
    A = rand();
    fi = rand();
    omega = (omega0/n)*(i);
    x(1,t+1) = x(1,t+1) + A*sin(omega*t+fi);
end
end
