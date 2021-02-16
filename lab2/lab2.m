x = signal_generator(12);
N = 1024;
Fd=1024;% Частота дискретизации (Гц)
FftL=1024;% Количество линий Фурье спектра

disp('Лаб №2.1')
F1 = complex(zeros(1,N));
for p=1:N
    for k = 1:N
        F1(1,p) = F1(1,p) + x(k)*(cos(-2*pi*p*k/N)-sin(-2*pi*p*k/N)*1i);
    end
end
FftS1=abs(F1);% Амплитуды преобразования Фурье сигнала

FftS1=2*FftS1./FftL;% Нормировка спектра по амплитуде
FftS1(1)=FftS1(1)/2;% Нормировка постоянной составляющей в спектре
F=0:Fd/FftL:Fd/2-1/FftL;% Массив частот вычисляемого спектра Фурье

figure
plot(F,FftS1(1:length(F)));% Построение спектра Фурье сигнала
hold on;
title('Спектр сигнала (ДПФ)');
xlabel('Частота');
ylabel('Амплитуда');

Xpositive1 = FftS1(1:length(F));

[~,locs1] = findpeaks(Xpositive1,'MinPeakHeight',0.02,...
    'MinPeakDistance',2);

plot(F(locs1),Xpositive1(locs1),'rv','MarkerFaceColor','r');
cellpeaks = cellstr(num2str(round(F(locs1)',-1)));
text(F(locs1),Xpositive1(locs1),cellpeaks,'FontSize',16);
hold off;
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
disp('Лаб №2.2.1')
F2 = complex(zeros(1,N));
w=zeros(2,N);

for i = 1:N
    if i<=N/8
        w(1,i)=cos(2*pi*i/N);
        w(2,i)=sin(2*pi*i/N);
    elseif i <=N/4
        w(1,i)=w(2,N/4+1-i);
        w(2,i)=w(1,N/4+1-i);
    elseif i<=N/2
        w(1,i)=-w(2,i-N/4);
        w(2,i)=+w(1,i-N/4);
    else
        w(1,i)=-w(1,i-N/2);
        w(2,i)=-w(2,i-N/2);
    end
end
for p=1:N
    for k = 1:N
        a=mod(p*k,N);
        F2(1,p) = F2(1,p) + x(k)*(w(1,a+1)-w(2,a+1)*1i);
    end
end
FftS2=abs(F2);% Амплитуды преобразования Фурье сигнала
FftS2=2*FftS2./FftL;% Нормировка спектра по амплитуде
FftS2(1)=FftS2(1)/2;% Нормировка постоянной составляющей в спектре

figure
plot(F,FftS2(1:length(F)));% Построение спектра Фурье сигнала
hold on;
title('Спектр сигнала(БПФ)');
xlabel('Частота');
ylabel('Амплитуда');

Xpositive2 = FftS2(1:length(F));

[~,locs] = findpeaks(Xpositive2,'MinPeakHeight',0.02,...
    'MinPeakDistance',2);

plot(F(locs),Xpositive2(locs),'rv','MarkerFaceColor','r');
cellpeaks = cellstr(num2str(round(F(locs)',-1)));
text(F(locs),Xpositive2(locs),cellpeaks,'FontSize',16);
hold off;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
disp('Лаб №2.2.2')
FftS=abs(fft(x,FftL));

FftS=2*FftS./FftL;% Нормировка спектра по амплитуде
FftS(1)=FftS(1)/2;% Нормировка постоянной составляющей в спектре

figure
plot(F,FftS(1:length(F)));% Построение спектра Фурье сигнала
hold on;
title('Спектр сигнала(БПФ, встроенная функция)');
xlabel('Частота');
ylabel('Амплитуда');

Xpositive = FftS(1:length(F));

[~,locs] = findpeaks(Xpositive,'MinPeakHeight',0.02,...
    'MinPeakDistance',2);

plot(F(locs),Xpositive(locs),'rv','MarkerFaceColor','r');
cellpeaks = cellstr(num2str(round(F(locs)',-1)));
text(F(locs),Xpositive(locs),cellpeaks,'FontSize',16);
hold off;