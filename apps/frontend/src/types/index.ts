export interface User {
  id: string;
  email: string;
  firstName: string;
  lastName: string;
  avatar?: string;
  role: 'ADMIN' | 'USER' | 'MODERATOR';
  isActive: boolean;
  createdAt: string;
  lastLogin?: string;
}

export interface Activity {
  id: string;
  userId: string;
  user?: User;
  type: 'LOGIN' | 'LOGOUT' | 'EVENT_CREATED' | 'EVENT_UPDATED' | 'EVENT_DELETED' | 'USER_REGISTERED';
  description: string;
  metadata?: Record<string, any>;
  timestamp: string;
  ipAddress?: string;
}

export interface Event {
  id: string;
  title: string;
  description: string;
  type: 'CONFERENCE' | 'WORKSHOP' | 'MEETUP' | 'WEBINAR' | 'COMPETITION';
  status: 'DRAFT' | 'PUBLISHED' | 'CANCELLED' | 'COMPLETED';
  startDate: string;
  endDate: string;
  location?: string;
  maxParticipants?: number;
  currentParticipants: number;
  organizerId: string;
  organizer?: User;
  createdAt: string;
  updatedAt: string;
}

export interface DashboardStats {
  totalUsers: number;
  activeUsers: number;
  totalEvents: number;
  upcomingEvents: number;
  completedEvents: number;
  totalActivities: number;
} 