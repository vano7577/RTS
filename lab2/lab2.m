X=zeros(3,1024);
x(1,:)=signal_generator(12,1024);
x(2,:)=signal_generator(12,1024);
x(3,:)=signal_generator(12,1024);
N = 1024;
Fd=1024;% Частота дискретизации
[F_DFT, title_DFT] = FT.DFT(x(1,:),N);
[F_FFT_h_W, title_FFT_h_W]= FT.FFT_handmade_without_W  (x(1,:),N);
[F_FFT_h, title_FFT_h]= FT.FFT_handmade (x(1,:),N);
[F_FFT_m, title_FFT_m]=  FT.FFT_matlab(x(1,:));
[A_lenH_DFT, H1]= FT.plot_FT (F_DFT,title_DFT,Fd,N);
[A_lenH_FFT, H2]= FT.plot_FT (F_FFT_h,title_FFT_h,Fd,N);
FT.plot_FT (F_FFT_h_W,title_FFT_h_W,Fd,N);
FT.plot_FT (F_FFT_m,title_FFT_m,Fd,N);
FT.compare_deltaA_FFT_DFT (A_lenH_DFT, A_lenH_FFT, H1);
f_DFT=@() FT.DFT(x(1,:),N);
f_FFT=@() FT.FFT_handmade(x(1,:),N);
t_DFT=MD.time_f(f_DFT,'ДПФ');
t_FFT=MD.time_f(f_FFT,'БПФ');
MD.difference_time (t_DFT,t_FFT, 'ДПФ', 'БПФ');
[numRows,numCols ] = size(X);
for i=1:numRows
    FT.compare_time_FFT(X(i,:), N);
end