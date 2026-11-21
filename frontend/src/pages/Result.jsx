import { useEffect, useState } from 'react';
import { Link, Navigate, useLocation } from 'react-router-dom';
import { getLeaderboard } from '../api';

export default function Result() {
  const { state } = useLocation();
  const result = state?.result;
  const [leaderboard, setLeaderboard] = useState([]);

  useEffect(() => {
    if (result) {
      getLeaderboard(result.quizId).then(setLeaderboard).catch(() => setLeaderboard([]));
    }
  }, [result]);

  // Opened /result directly (e.g. after a refresh) - there is nothing to show.
  if (!result) return <Navigate to="/" replace />;

  const percent = Math.round((result.score / result.total) * 100);

  return (
    <>
      <div className="panel center">
        <h1>{result.quizTitle}</h1>
        <p>Well played, {result.playerName}!</p>
        <div className="score">{result.score} / {result.total}</div>
        <p className="subtitle">{percent}% correct</p>
        <div className="nav-row center-row">
          <Link className="btn" to={`/quiz/${result.quizId}`}>Try again</Link>
          <Link className="btn secondary" to="/">All quizzes</Link>
        </div>
      </div>

      <h2>Review</h2>
      {result.review.map((item, i) => {
        const correct = item.selectedIndex === item.correctIndex;
        return (
          <div className={`review ${correct ? 'correct' : 'wrong'}`} key={item.questionId}>
            <strong>{i + 1}. {item.text}</strong>
            <p>
              Your answer:{' '}
              {item.selectedIndex === null || item.selectedIndex === undefined
                ? 'Not answered'
                : item.options[item.selectedIndex]}
            </p>
            {!correct && <p>Correct answer: {item.options[item.correctIndex]}</p>}
          </div>
        );
      })}

      <h2>Leaderboard</h2>
      {leaderboard.length === 0 ? (
        <p className="message">No scores yet.</p>
      ) : (
        <table className="table">
          <thead>
            <tr><th>#</th><th>Player</th><th>Score</th><th>Date</th></tr>
          </thead>
          <tbody>
            {leaderboard.map((row, i) => (
              <tr key={i}>
                <td>{i + 1}</td>
                <td>{row.playerName}</td>
                <td>{row.score} / {row.total}</td>
                <td>{new Date(row.takenAt).toLocaleDateString()}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </>
  );
}
