FROM node:16-alpine3.15
WORKDIR /frontend
COPY package.json package-lock.json ./
RUN npm ci
COPY . .
EXPOSE 3000
EXPOSE 24678
ENV NODE_ENV production
CMD ["npm", "run", "dev", "--", "--host", "0.0.0.0"]