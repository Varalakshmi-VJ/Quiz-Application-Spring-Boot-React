import { Link, Route, Routes } from 'react-router-dom';
import Home from './pages/Home.jsx';
import Quiz from './pages/Quiz.jsx';
import Result from './pages/Result.jsx';

export default function App() {
  return (
    <>
      <header className="navbar">
        <div className="container">
          <Link to="/" className="brand">Quiz App</Link>
        </div>
      </header>
      <main className="container">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/quiz/:id" element={<Quiz />} />
          <Route path="/result" element={<Result />} />
          <Route path="*" element={<p className="message">Page not found. <Link to="/">Go home</Link></p>} />
        </Routes>
      </main>
    </>
  );
}
