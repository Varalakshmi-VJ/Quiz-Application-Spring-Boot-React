import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getQuizzes } from '../api';

export default function Home() {
  const [quizzes, setQuizzes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    getQuizzes()
      .then(setQuizzes)
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <p className="message">Loading quizzes...</p>;
  if (error) return <p className="message error">{error}</p>;

  return (
    <>
      <h1>Choose a quiz</h1>
      <p className="subtitle">Pick a topic, answer the questions and see your score.</p>
      <div className="grid">
        {quizzes.map((quiz) => (
          <div className="card" key={quiz.id}>
            <span className="badge">{quiz.topic}</span>
            <h2>{quiz.title}</h2>
            <p>{quiz.description}</p>
            <div className="card-footer">
              <small>{quiz.questionCount} questions</small>
              <Link className="btn" to={`/quiz/${quiz.id}`}>Start</Link>
            </div>
          </div>
        ))}
      </div>
    </>
  );
}
