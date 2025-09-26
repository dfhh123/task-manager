import { create } from 'zustand';

interface ModalState {
  isLoginOpen: boolean;
  isRegisterOpen: boolean;
  
  // Actions
  openLogin: () => void;
  openRegister: () => void;
  closeModals: () => void;
}

export const useModalStore = create<ModalState>((set) => ({
  isLoginOpen: false,
  isRegisterOpen: false,

  openLogin: () => set({ isLoginOpen: true, isRegisterOpen: false }),
  openRegister: () => set({ isLoginOpen: false, isRegisterOpen: true }),
  closeModals: () => set({ isLoginOpen: false, isRegisterOpen: false }),
}));
