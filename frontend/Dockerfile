FROM node:16-alpine3.15
WORKDIR /frontend
COPY . .
RUN npm ci
EXPOSE 3000
RUN npm run dev -- --host --port 3000