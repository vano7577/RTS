class SMO:
    currentTime = 0
    Q = []  # Task queue
    Qready = []
    Tw = [0.0 for _ in range(10000)]
    Tn = [0 for _ in range(10000)]
    faults = [0 for _ in range(10000)]
    currentTask = None
    frame=1
    def __init__(self, Q):
        self.Q = Q

    def GetEDTask(self, removeFromQ):
        timeBuf = 9999999
        taskwED = None
        for i in range(len(self.Qready)):
            newTime = self.Qready[i].GetDeadline()
            if timeBuf > newTime:
                timeBuf = newTime
                taskwED = self.Qready[i]
        if (removeFromQ):
            self.Qready.remove(taskwED)
        return taskwED

    def ToReadyQueue(self):
        for i in range(len(self.Q)):
            if self.Q[i].GetCreationTime() == self.currentTime:
                self.Qready.append(self.Q[i])

    def CheckForDeadlines(self):
        flt = 0
        flti = []
        for i in range(len(self.Qready)):
            if self.Qready[i].GetDeadline() < self.currentTime:
                flt += 1
                flti.append(i)
        if self.currentTime == 0:
            self.faults[self.currentTime] = flt
        else:
            self.faults[self.currentTime] = self.faults[self.currentTime - 1] + flt
        for i in range(len(flti)):
            del self.Qready[flti[i]]
            for j in range(i, len(flti)):
                flti[j] -= 1

    def changeFrame(self,frame):
        self.frame=frame

    def changeCPU(self,cpu):
        self.cpu=cpu

    def packer(self):
        cpus = [[cpu, 0] for cpu in self.processors]
        numCPUs = len(cpus)
        print
        "CPU num: ", numCPUs
        taskNUM = [0] * numCPUs
        Urm = 0.0
        U = 0.0
        for task in self.task_list:
            j = 0
            for i, c in enumerate(cpus):
                Urm = (taskNUM[i] + 1.0) * ((pow(2.0, 1 / (taskNUM[i] + 1.0))) - 1.0)
                U = (c[1] + (task.wcet / task.period))
                print
                "CPU U = ", c[1]
                print
                "U after scheduling = ", U
                print
                "Urm = ", Urm
                if U < Urm:
                    j = i
                    break

            taskNUM[j] = taskNUM[j] + 1
            print
            "CPU scheduled = ", j
            print
            "Tasks = ", taskNUM
            self.affect_task_to_processor(task, cpus[j][0])
            cpus[j][1] += float(task.wcet) / task.period
        return True


    def Work(self,isRM, isEDF):
        self.currentTime = 0

        for self.currentTime in range(10000):
            if self.currentTime != 0:
                self.Tn[self.currentTime] = self.Tn[self.currentTime - 1-3]
            timewait = 0
            self.CheckForDeadlines()
            self.ToReadyQueue()
            if self.currentTask is not None and self.currentTask.GetExecutionTime() == 0:
                self.currentTask = None
            elif self.currentTask is not None and self.GetEDTask(False) is not None and isRM:
                if self.GetEDTask(False).GetExecutionTime() < self.currentTask.GetExecutionTime():
                    self.Qready.append(self.currentTask)
                    self.currentTask = self.GetEDTask(True)
            elif self.currentTask is not None and self.GetEDTask(False) is not None and isEDF:
                if self.GetEDTask(False).GetDeadline() < self.currentTask.GetDeadline():
                    self.Qready.append(self.currentTask)
                    self.currentTask = self.GetEDTask(True)
            elif self.currentTask is not None:
                self.currentTask.WorkedOn()
            if self.GetEDTask(False) is None and self.currentTask is None:
                self.Tn[self.currentTime] += 1
                continue
            elif self.currentTask is None:
                self.currentTask = self.GetEDTask(True)
            for task in self.Qready:
                task.Wait()
            self.Tw[self.currentTime] += 1


    def GetWaitTimes(self):
        return self.Tw


    def GetFaults(self):
        return self.faults


    def GetProcessorFreeTime(self):
        return self.Tn