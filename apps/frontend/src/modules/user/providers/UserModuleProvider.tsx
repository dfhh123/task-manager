import React, { createContext, useContext, useReducer, ReactNode } from 'react';

// Типы для состояния модуля
interface UserState {
  users: User[];
  currentUser: User | null;
  loading: boolean;
  error: string | null;
}

interface User {
  id: string;
  name: string;
  email: string;
  role: string;
}

// Actions для управления состоянием
type UserAction = 
  | { type: 'SET_USERS'; payload: User[] }
  | { type: 'SET_CURRENT_USER'; payload: User | null }
  | { type: 'SET_LOADING'; payload: boolean }
  | { type: 'SET_ERROR'; payload: string | null };

// Context
interface UserModuleContextType {
  state: UserState;
  dispatch: React.Dispatch<UserAction>;
  // Методы модуля
  fetchUsers: () => Promise<void>;
  updateUser: (id: string, data: Partial<User>) => Promise<void>;
}

const UserModuleContext = createContext<UserModuleContextType | undefined>(undefined);

// Reducer
const userReducer = (state: UserState, action: UserAction): UserState => {
  switch (action.type) {
    case 'SET_USERS':
      return { ...state, users: action.payload };
    case 'SET_CURRENT_USER':
      return { ...state, currentUser: action.payload };
    case 'SET_LOADING':
      return { ...state, loading: action.payload };
    case 'SET_ERROR':
      return { ...state, error: action.payload };
    default:
      return state;
  }
};

// Provider
interface UserModuleProviderProps {
  children: ReactNode;
}

export const UserModuleProvider: React.FC<UserModuleProviderProps> = ({ children }) => {
  const [state, dispatch] = useReducer(userReducer, {
    users: [],
    currentUser: null,
    loading: false,
    error: null,
  });

  // Методы модуля
  const fetchUsers = async () => {
    dispatch({ type: 'SET_LOADING', payload: true });
    try {
      // Здесь будет API вызов
      const users = await fetch('/api/users').then(res => res.json());
      dispatch({ type: 'SET_USERS', payload: users });
    } catch (error) {
      dispatch({ type: 'SET_ERROR', payload: 'Failed to fetch users' });
    } finally {
      dispatch({ type: 'SET_LOADING', payload: false });
    }
  };

  const updateUser = async (id: string, data: Partial<User>) => {
    dispatch({ type: 'SET_LOADING', payload: true });
    try {
      // API вызов для обновления
      await fetch(`/api/users/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
      });
      
      // Обновляем локальное состояние
      const updatedUsers = state.users.map(user => 
        user.id === id ? { ...user, ...data } : user
      );
      dispatch({ type: 'SET_USERS', payload: updatedUsers });
    } catch (error) {
      dispatch({ type: 'SET_ERROR', payload: 'Failed to update user' });
    } finally {
      dispatch({ type: 'SET_LOADING', payload: false });
    }
  };

  const value: UserModuleContextType = {
    state,
    dispatch,
    fetchUsers,
    updateUser,
  };

  return (
    <UserModuleContext.Provider value={value}>
      {children}
    </UserModuleContext.Provider>
  );
};

// Hook для использования контекста
export const useUserModule = () => {
  const context = useContext(UserModuleContext);
  if (context === undefined) {
    throw new Error('useUserModule must be used within a UserModuleProvider');
  }
  return context;
};
