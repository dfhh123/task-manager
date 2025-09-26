import { create } from 'zustand';
import { persist } from 'zustand/middleware';

export interface Task {
  id: string;
  title: string;
  description: string;
  completed: boolean;
  createdAt: string;
  updatedAt: string;
  userId: string;
}

interface TaskState {
  tasks: Task[];
  isLoading: boolean;
  error: string | null;
  
  // Actions
  createTask: (title: string, description: string) => Promise<void>;
  updateTask: (id: string, updates: Partial<Pick<Task, 'title' | 'description' | 'completed'>>) => Promise<void>;
  deleteTask: (id: string) => Promise<void>;
  toggleTask: (id: string) => Promise<void>;
  clearError: () => void;
  
  // Getters
  getCompletedTasks: () => Task[];
  getPendingTasks: () => Task[];
  getTasksStats: () => { completed: number; pending: number; total: number };
}

export const useTaskStore = create<TaskState>()(
  persist(
    (set, get) => ({
      tasks: [], // В демо режиме будут загружены демо данные
      isLoading: false,
      error: null,

      createTask: async (title: string, description: string) => {
        set({ isLoading: true, error: null });
        
        try {
          // Симуляция API запроса
          await new Promise(resolve => setTimeout(resolve, 500));
          
          const newTask: Task = {
            id: Date.now().toString(),
            title,
            description,
            completed: false,
            createdAt: new Date().toISOString(),
            updatedAt: new Date().toISOString(),
            userId: '1', // В реальном приложении брать из auth store
          };
          
          set(state => ({
            tasks: [...state.tasks, newTask],
            isLoading: false
          }));
        } catch (error) {
          set({ 
            error: error instanceof Error ? error.message : 'Ошибка создания задачи',
            isLoading: false 
          });
        }
      },

      updateTask: async (id: string, updates: Partial<Pick<Task, 'title' | 'description' | 'completed'>>) => {
        set({ isLoading: true, error: null });
        
        try {
          // Симуляция API запроса
          await new Promise(resolve => setTimeout(resolve, 300));
          
          set(state => ({
            tasks: state.tasks.map(task =>
              task.id === id
                ? { ...task, ...updates, updatedAt: new Date().toISOString() }
                : task
            ),
            isLoading: false
          }));
        } catch (error) {
          set({ 
            error: error instanceof Error ? error.message : 'Ошибка обновления задачи',
            isLoading: false 
          });
        }
      },

      deleteTask: async (id: string) => {
        set({ isLoading: true, error: null });
        
        try {
          // Симуляция API запроса
          await new Promise(resolve => setTimeout(resolve, 300));
          
          set(state => ({
            tasks: state.tasks.filter(task => task.id !== id),
            isLoading: false
          }));
        } catch (error) {
          set({ 
            error: error instanceof Error ? error.message : 'Ошибка удаления задачи',
            isLoading: false 
          });
        }
      },

      toggleTask: async (id: string) => {
        const task = get().tasks.find(t => t.id === id);
        if (task) {
          await get().updateTask(id, { completed: !task.completed });
        }
      },

      clearError: () => {
        set({ error: null });
      },

      getCompletedTasks: () => {
        return get().tasks.filter(task => task.completed);
      },

      getPendingTasks: () => {
        return get().tasks.filter(task => !task.completed);
      },

      getTasksStats: () => {
        const tasks = get().tasks;
        const completed = tasks.filter(task => task.completed).length;
        const pending = tasks.filter(task => !task.completed).length;
        return { completed, pending, total: tasks.length };
      },
    }),
    {
      name: 'tasks-storage',
    }
  )
);
