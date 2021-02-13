function x = signal_generator(n)
omega0 = 2400;
N = 1024;
x= zeros(1,N);
t = 1:N;
for i = 1:n
    A = rand();
    fi = rand();
    omega = (omega0/n)*(i);
    x(1,t) = x(1,t) + A*sin(omega*t+fi);
end
end
