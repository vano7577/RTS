classdef FT
     methods (Static)
        function [F, title_FT] = DFT(x,N)
            F = complex(zeros(1,N));
            title_FT = 'Спектр сигнала (ДПФ)';
            for p=0:N-1
                for k = 0:N-1
                    F(1,p+1) = F(1,p+1) + x(k+1)*(cos(2*pi*p*k/N)-sin(2*pi*p*k/N)*1i);
                end
            end
        end
        function [F, title_FT]=FFT_handmade (x,N)
            F = complex(zeros(1,N));
            title_FT= 'Спектр сигнала (БПФ)';
            w=zeros(2,N);
            for i = 0:N-1
                if i<=N/8
                    w(1,i+1)=cos(2*pi*i/N);
                    w(2,i+1)=sin(2*pi*i/N);
                elseif i <=N/4
                    w(1,i+1)=w(2,N/4-i+1);
                    w(2,i+1)=w(1,N/4-i+1);
                elseif i<=N/2
                    w(1,i+1)=-w(2,i-N/4+1);
                    w(2,i+1)=+w(1,i-N/4+1);
                else
                    w(1,i+1)=-w(1,i-N/2+1);
                    w(2,i+1)=-w(2,i-N/2+1);
                end
            end
            for p=0:N-1
                for k = 0:N-1
                    a=mod(p*k,N);
                    F(1,p+1) = F(1,p+1) + x(k+1)*(w(1,a+1)-w(2,a+1)*1i);
                end
            end
        end
        function [F, title_FT]=FFT_matlab(x)
            F=fft(x);
            title_FT = 'Спектр сигнала(БПФ, встроенная функция)';
        end
        function [A_lenH, H]=plot_FT (F,title_FT,Fd,N)
            A=abs(F);% Амплитуды преобразования Фурье сигнала
            A=2*A./N;% Нормировка спектра по амплитуде
            A(1)=A(1)/2;% Нормировка постоянной составляющей в спектре
            H=0:Fd/N:Fd/2-1/N;% Массив частот вычисляемого спектра Фурье
            A_lenH = A(1:length(H));
            
            figure
            plot(H,A_lenH);% Построение спектра Фурье сигнала
            hold on;
            title(title_FT);
            xlabel('Частота');
            ylabel('Амплитуда');
            [~,locs1] = findpeaks(A_lenH,'MinPeakHeight',0.02,...
                'MinPeakDistance',2);
            plot(H(locs1),A_lenH(locs1),'rv','MarkerFaceColor','r');
            cellpeaks = cellstr(num2str(round(H(locs1)',-1)));
            text(H(locs1),A_lenH(locs1),cellpeaks,'FontSize',16);
            hold off;
            saveas(gcf, sprintf('./res/%s.jpg',title_FT))
        end
        function compare_deltaA_FFT_DFT (A_lenH_DFT, A_lenH_FFT, H)
            figure
            plot(H,A_lenH_FFT-A_lenH_DFT);
            hold on;
            title('Отклонение ФПФ от ДПФ (обратить внимание на степень отклонения)');
            xlabel('Частота');
            ylabel('Отклонение');
            saveas(gcf,'./res/compare_deltaA_FFT_DFT.jpg');
        end
    end
end








