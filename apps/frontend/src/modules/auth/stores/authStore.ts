import { create } from 'zustand';
import { persist } from 'zustand/middleware';

export interface User {
  id: string;
  email: string;
  name: string;
  createdAt: string;
}

interface AuthState {
  user: User | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  error: string | null;
  
  // Actions
  login: (email: string, password: string) => Promise<void>;
  register: (email: string, password: string, name: string) => Promise<void>;
  logout: () => void;
  clearError: () => void;
}

// Моковые пользователи для демо
const mockUsers: User[] = [
  {
    id: '1',
    email: 'demo@example.com',
    name: 'Demo User',
    createdAt: new Date().toISOString(),
  }
];

export const useAuthStore = create<AuthState>()(
  persist(
    (set, get) => ({
      user: null,
      isAuthenticated: false,
      isLoading: false,
      error: null,

      login: async (email: string, password: string) => {
        set({ isLoading: true, error: null });
        
        try {
          // Симуляция API запроса
          await new Promise(resolve => setTimeout(resolve, 1000));
          
          // Простая проверка для демо
          if (email === 'demo@example.com' && password === 'demo123') {
            const user = mockUsers[0];
            set({ 
              user, 
              isAuthenticated: true, 
              isLoading: false 
            });
          } else {
            throw new Error('Неверный email или пароль');
          }
        } catch (error) {
          set({ 
            error: error instanceof Error ? error.message : 'Ошибка авторизации',
            isLoading: false 
          });
        }
      },

      register: async (email: string, password: string, name: string) => {
        set({ isLoading: true, error: null });
        
        try {
          // Симуляция API запроса
          await new Promise(resolve => setTimeout(resolve, 1000));
          
          // Простая проверка для демо
          if (email === 'demo@example.com') {
            throw new Error('Пользователь с таким email уже существует');
          }
          
          const newUser: User = {
            id: Date.now().toString(),
            email,
            name,
            createdAt: new Date().toISOString(),
          };
          
          mockUsers.push(newUser);
          
          set({ 
            user: newUser, 
            isAuthenticated: true, 
            isLoading: false 
          });
        } catch (error) {
          set({ 
            error: error instanceof Error ? error.message : 'Ошибка регистрации',
            isLoading: false 
          });
        }
      },

      logout: () => {
        set({ 
          user: null, 
          isAuthenticated: false,
          error: null 
        });
      },

      clearError: () => {
        set({ error: null });
      },
    }),
    {
      name: 'auth-storage',
      partialize: (state) => ({ 
        user: state.user, 
        isAuthenticated: state.isAuthenticated 
      }),
    }
  )
);
