classdef MD
    methods (Static)
        function plot_signal (x)
            plot(x);
            title('Случайный сигнал')
            xlabel('t')
            ylabel('x(t)')
            saveas(gcf, './res/signal.jpg')
        end
        function M (x)
            M=mean(x);
            fprintf('Математическое ожидание\n M = %f\n\n',M);
        end
        function D (x)
            Dx=var(x);
            fprintf('Дисперсия\n D = %f\n\n',Dx);
        end
        function a= On ()
            a= zeros(2,120);
            for n = 1:120
                y= @() signal_generator(n,1024);
                a(1,n) = n;
                a(2,n) = timeit(y);
            end
            figure
            plot(a(2,:));
            title('Сложность алгоритма')
            xlabel('n')
            ylabel('O(n)')
            saveas(gcf, './res/On.jpg')
        end
        function Mx (k)
            b=zeros(1,k);
            c=1:k;
            for p=1:k
                b(1,p)=mean(signal_generator(12, p));
            end
            figure
            plot(c, b(1,:));
            title('зависимость мат ожидания от количества дискретных отсчетов')
            xlabel('N')
            ylabel('Mx')
            saveas(gcf, './res/MxN.jpg')
        end
        function [c,lags] = corr (x)
            [c,lags] = xcorr(x,'normalized');
        end
        function t= time_f (f, title_f)
            t=timeit(f);
            fprintf('Время выполнения %s функции\nt(%s) = %f\n\n',title_f,title_f,t);
        end
        function [c,lags] = two_corr (x, x1)
            [c,lags] = xcorr(x,x1,'normalized');
        end
        function plot_corr (lags, c, title_s)
            figure
            plot(lags,c)
            title(sprintf('Нормированная %s функция', title_s))
            xlabel('τ')
            ylabel('Ψ(τ)')
            saveas(gcf, sprintf('./res/norm_%s.jpg',title_s))
        end
        function writeOn (a)
            writematrix(a,'./res/O.csv');
        end
        function difference_time (t,t1, title_f, title_f1)
            fprintf('Соотношение времени выполнения %s и %s функций\nt(%s)/t(%s)  = %f\n\n',title_f, title_f1, title_f, title_f1,t/t1);
        end
    end
end