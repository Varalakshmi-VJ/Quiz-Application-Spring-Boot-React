const BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8081/api';

async function request(path, options = {}) {
  let response;
  try {
    response = await fetch(`${BASE_URL}${path}`, {
      headers: { 'Content-Type': 'application/json' },
      ...options,
    });
  } catch {
    throw new Error('Cannot reach the server. Is the Spring Boot backend running?');
  }

  if (!response.ok) {
    let message = 'Something went wrong. Please try again.';
    try {
      const data = await response.json();
      if (data.message) message = data.message;
    } catch {
      /* response had no JSON body */
    }
    throw new Error(message);
  }
  return response.json();
}

export const getQuizzes = () => request('/quizzes');

export const getQuiz = (id) => request(`/quizzes/${id}`);

export const submitQuiz = (id, payload) =>
  request(`/quizzes/${id}/submit`, { method: 'POST', body: JSON.stringify(payload) });

export const getLeaderboard = (id) => request(`/quizzes/${id}/leaderboard`);
